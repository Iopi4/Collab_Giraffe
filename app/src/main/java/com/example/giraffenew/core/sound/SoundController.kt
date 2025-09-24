package com.example.giraffenew.core.sound

import android.app.NotificationManager
import com.example.giraffenew.R
import android.content.Context
import android.health.connect.datatypes.units.Volume
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
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
@Inject constructor(@ApplicationContext private val context: Context) {
    private val audioManager: AudioManager by lazy {
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    private val soundPool = SoundPool.Builder().setMaxStreams(4).build()

    private val soundConfigs = mapOf(
        278 to SoundConfigRepeat(R.raw.beep_sound_weight, 500, 4500),
        279 to SoundConfigRepeat(R.raw.beep_sound_warning, 500, 4500),
        280 to SoundConfigRepeat(R.raw.beep_sound_alarm_2, 500, 500),
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
    private val singlePlayRegisters = setOf(280, 281, 282,)
    private val lastState = mutableMapOf<Int, Boolean>()

    //Поле для хранения текущей громкости
    private var currentVolume: Float = 0.1f

    //Метод для установки громкости сигналов
    fun setVolume(volume: Float){
        currentVolume = volume.coerceIn(0.1f, 0.5f) //Установили min и max значения
    }



    init {
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

    private fun disableSilentMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (notificationManager.isNotificationPolicyAccessGranted) {
                notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
            }
        }
    }

    private fun setMaxVolume() {
        audioManager.setStreamVolume(
            AudioManager.STREAM_NOTIFICATION,
            audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION),
            0
        )
        audioManager.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
            0
        )
    }


    private val singleSoundIds = mutableMapOf<Int, Int>()

    init {
        // Предзагрузка звука для запрета доступа
        preloadSound(R.raw.voice_access_denied_half_life)
        preloadSound(R.raw.beep_sound_neaktive_knopka_half_life)
    }

    private fun preloadSound(resourceId: Int) {
        val soundId = soundPool.load(context, resourceId, 1)
        singleSoundIds[resourceId] = soundId
    }

    fun playSoundOnce(resourceId: Int) {
        setMaxVolume()
        disableSilentMode()

        singleSoundIds[resourceId]?.let { soundId ->
            soundPool.play(soundId, currentVolume, currentVolume, 1, 0, 1.0f)
        }
    }


    fun setRegisterState(register: Int, active: Boolean) {
        setMaxVolume()
        disableSilentMode()

        val config = soundConfigs[register] ?: return
        val soundId = soundIds[register] ?: return

        val wasActive = lastState[register] ?: false

        if (register in singlePlayRegisters) {
            if (active && !wasActive) {
                //Volume 0.1f - это 74dB. 0.5f - это 85dB. 1.0f - это 91dB. Оставляем 0.3f
                soundPool.play(soundId, currentVolume, currentVolume, 1, 0, 1.0f)
            }
        } else {
            val job = jobs[register]
            if (active) {
                if (job != null && job.isActive) return
                jobs[register] = CoroutineScope(Dispatchers.Main).launch {
                    while (isActive) {
                        soundPool.play(soundId, 0.1f, 0.1f, 1, 0, 1.0f)
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

    fun release() {
        jobs.values.forEach { it?.cancel() }
        soundPool.release()
    }
}