package com.example.giraffenew.core.sound

import com.example.giraffenew.R
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
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
class SoundController @Inject constructor(@ApplicationContext private val context: Context) {
    private val soundConfigs = mapOf(
        177 to SoundConfig(R.raw.beep_sound_attention, 500, 2000),
        178 to SoundConfig(R.raw.beep_sound_dwn, 500, 2000),
        179 to SoundConfig(R.raw.beep_sound_stop, 5000, 2000),
        180 to SoundConfig(R.raw.beep_sound_weight, 5000, 2000)
    )

        private data class SoundConfig(
            val resourceId: Int,
            val duration: Long,
            val pause: Long
        )

        private val mediaPlayers = soundConfigs.mapValues { (_, config) ->
            MediaPlayer.create(context, config.resourceId).apply {
                setOnErrorListener { mp, what, extra ->
                    Log.e("SoundController", "Error playing sound: what=$what, extra=$extra")
                    true
                }
            }
        }

        private val jobs = mutableMapOf<Int, Job?>()

        fun setRegisterState(register: Int, active: Boolean) {
            val job = jobs[register]
            val mediaPlayer = mediaPlayers[register] ?: return
            val config = soundConfigs[register] ?: return

            if (active) {
                if (job != null && job.isActive) return

                jobs[register] = CoroutineScope(Dispatchers.Main).launch {
                    while (isActive) {
                        mediaPlayer.start()
                        delay(config.duration + config.pause)
                    }
                }
            } else {
                job?.cancel()
                jobs[register] = null
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                }
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        mediaPlayer.prepare()
                    } catch (e: Exception) {
                        Log.e("SoundController", "Prepare error: ${e.message}")
                    }
                }
            }
        }

        fun release() {
            jobs.values.forEach { it?.cancel() }
            mediaPlayers.values.forEach { it.release() }
        }
}