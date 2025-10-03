package com.example.giraffenew.core.sound

import android.app.NotificationManager
import com.example.giraffenew.R
import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.util.Log
import com.example.giraffenew.data.repositories.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundController
@Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsRepository: SettingsRepository,

    ) {
    //private val audioManager: AudioManager by lazy { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    private var audioManager: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private var originalVolume: Int = 1
    private var originalStreamType: Int = AudioManager.STREAM_MUSIC
    private var isOriginalVolumeSaved = false
    private val soundPool = SoundPool.Builder().setMaxStreams(4).build()

    private val soundConfigs = mapOf(
        278 to SoundConfigRepeat(R.raw.beep_sound_weight, 3000, 4500),
        279 to SoundConfigRepeat(R.raw.beep_sound_warning, 3000, 4500),
        280 to SoundConfigRepeat(R.raw.beep_sound_alarm_2, 3000, 500),
        281 to SoundConfigRepeat(R.raw.karlson_ti_shto_s_yma_soshol, 3000, 500),
        282 to SoundConfigRepeat(R.raw.voice_stoi, 1000, 500),
    )

    private data class SoundConfigRepeat(
        val resourceId: Int,
        val duration: Long,
        val pause: Long
    )

    private val soundIds = mutableMapOf<Int, Int>()
    private val jobs = mutableMapOf<Int, Job?>()
    private val singlePlayRegisters = setOf(280, 281, 282)
    private val lastState = mutableMapOf<Int, Boolean>()
    private val singleSoundIds = mutableMapOf<Int, Int>()

    private fun preloadSound(resourceId: Int, duration: Long ) {
        val soundId = soundPool.load(context, resourceId, 1)
        singleSoundIds[resourceId] = soundId
    }


    init {
        // Предзагрузка звука для запрета доступа
        preloadSound(R.raw.voice_access_denied_half_life,3000)
        preloadSound(R.raw.beep_sound_neaktive_knopka_half_life, 1000)

        // Устанавливаем начальную громкость из сохраненных настроек
        val savedVolume = settingsRepository.getVolumeLevel()
        Log.d("SoundController", "Initializing with volume: $savedVolume")

        soundConfigs.forEach { (id, config) ->
            soundIds[id] = soundPool.load(context, config.resourceId, 1)
        }
    }

    /* Метод play класса SoundPool воспроизводит звук из ранее загруженного в SoundPool звука.
      - soundID: идентификатор звука, загруженного посредством метода load. Это значение, возвращенное load.
      - leftVolume: громкость для левого канала (диапазон от 0.0 до 1.0).
      - rightVolume: громкость для правого канала (диапазон от 0.0 до 1.0).
      - priority: приоритет воспроизведения (0 - самый низкий). Если одновременно должно воспроизводиться больше звуков, чем возможно, то SoundPool остановит воспроизведение звуков с низким приоритетом.
      - loop: режим повтора (0 - без повтора, -1 - вечный повтор, положительное число - количество повторов (например, 1 означает, что звук сыграет дважды: первый раз и один повтор)).
      - rate: скорость воспроизведения (1.0 - нормальная скорость, диапазон от 0.5 до 2.0).
                         */

    // Всегда получаем актуальное значение из SettingsRepository при каждом воспроизведении:
    //volume 0.1f - это 74dB. 0.5f - это 85dB. 1.0f - это 91dB. Оставляем 0.3f

    private fun playSound(soundId: Int, volume: Float) {
        Log.d("SoundController", "Playing sound ID: $soundId with volume: $volume")
        soundPool.play(soundId, volume, volume, 1, 0, 1.0f)
    }




    fun playSoundOnce(soundResId: Int) {
        try {
            val soundId = singleSoundIds[soundResId]
            if (soundId == null) {
                Log.e("SoundController", "Sound not preloaded: $soundResId")
                return
            }
            val volume = settingsRepository.getVolumeLevel()
            playSound(soundId, volume)
        } catch (e: Exception) {
            Log.e("SoundController", "Error playing sound: ${e.message}")
        }
    }



    fun setAppVolumeIndependent() {
        if (!isOriginalVolumeSaved) {
            // Сохраняем оригинальные настройки
            originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            isOriginalVolumeSaved = true
        }

        // Устанавливаем нашу громкость
        val volume = settingsRepository.getVolumeLevel()
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val targetVolume = (volume * maxVolume).toInt()

        audioManager.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            targetVolume,
            AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE
        )

        // Игнорируем режим "Не беспокоить"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (notificationManager.isNotificationPolicyAccessGranted) {
                audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    targetVolume,
                    AudioManager.FLAG_ALLOW_RINGER_MODES
                )
            }
        }
    }

    fun restoreOriginalVolume() {
        if (isOriginalVolumeSaved) {
            audioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                originalVolume,
                0
            )
            isOriginalVolumeSaved = false
        }
    }

    fun setRegisterState(register: Int, active: Boolean) {
        setSafeVolume()
        disableSilentMode()

        val currentVolume = settingsRepository.getVolumeLevel()
        Log.d("SoundController", "setRegisterState: register=$register, active=$active, volume=$currentVolume")

        val config = soundConfigs[register] ?: return
        val soundId = soundIds[register] ?: return

        val wasActive = lastState[register] ?: false

        if (register in singlePlayRegisters) {
            if (active && !wasActive) {
                playSound(soundId, currentVolume)
            }
        } else {
            val job = jobs[register]
            if (active) {
                if (job != null && job.isActive) return
                jobs[register] = CoroutineScope(Dispatchers.Main).launch {
                    while (isActive) {
                        playSound(soundId, currentVolume)
                        delay(config.duration + config.pause)
                    }
                }
            } else {
                job?.cancel()
                jobs[register] = null
            }
        }
        lastState[register] = active
    }

    private fun setSafeVolume() {
        try {
            // Безопасная установка громкости без изменения Do Not Disturb
            val currentVolume = settingsRepository.getVolumeLevel()

            // Устанавливаем громкость только для музыкального потока
            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)
            val targetVolume = (maxVolume * currentVolume).toInt()

            audioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                targetVolume.coerceIn(0, maxVolume),
                0
            )

            Log.d("SoundController", "Safe volume set to: $targetVolume (max: $maxVolume)")
        } catch (e: SecurityException) {
            Log.w("SoundController", "Cannot set volume due to security restrictions: ${e.message}")
        } catch (e: Exception) {
            Log.e("SoundController", "Error setting volume: ${e.message}")
        }
    }

    private fun disableSilentMode() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                if (notificationManager.isNotificationPolicyAccessGranted) {
                    notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
                } else {
                    Log.w("SoundController", "Notification policy access not granted")
                }
            }
        } catch (e: SecurityException) {
            Log.w("SoundController", "Cannot change silent mode due to security restrictions")
        } catch (e: Exception) {
            Log.e("SoundController", "Error disabling silent mode: ${e.message}")
        }
    }

    fun release() {
        jobs.values.forEach { it?.cancel() }
        soundPool.release()
    }
}