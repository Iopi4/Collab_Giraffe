package com.example.giraffenew.core.sound

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper


class SoundManager(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    private var handler: Handler = Handler(Looper.getMainLooper())
    private var isPlaying = false
    private var currentSoundResId: Int = 0
    private var delayBetweenRepeats: Long = 0
    private var soundDuration: Long = 0

    fun setSound(soundResId: Int, delayBetweenRepeats: Long = 1000) {
        this.currentSoundResId = soundResId
        this.delayBetweenRepeats = delayBetweenRepeats

        // Создаем временный MediaPlayer для определения длительности звука
        val tempMediaPlayer = MediaPlayer.create(context, soundResId)
        tempMediaPlayer.release()
    }

    fun startSound() {
        if (isPlaying) return

        isPlaying = true
        playSoundWithDelay()
    }

    fun stopSound() {
        isPlaying = false
        handler.removeCallbacksAndMessages(null)
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun playSoundWithDelay() {
        if (!isPlaying) return

        mediaPlayer = MediaPlayer.create(context, currentSoundResId)
        mediaPlayer?.apply {
            setOnCompletionListener {
                it.release()
                mediaPlayer = null

                // Запускаем следующий повтор после задержки
                handler.postDelayed({
                    playSoundWithDelay()
                }, delayBetweenRepeats)
            }

            start()
        }
    }

    fun isPlaying(): Boolean {
        return isPlaying
    }

    fun cleanup() {
        stopSound()
    }
}