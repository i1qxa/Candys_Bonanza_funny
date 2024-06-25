package com.nightriders.candylands.ui

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nightriders.candylands.R
import com.nightriders.candylands.domain.CANDYS_PREFS
import com.nightriders.candylands.domain.MUSIC_PREFS
import com.nightriders.candylands.domain.isMusickOn

class GameActivity : AppCompatActivity() {

    private var mMediaPlayer: MediaPlayer? = null
    private val prefs by lazy { getSharedPreferences(CANDYS_PREFS, Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        checkMusic()
        observeMusic()
    }

    private fun checkMusic(){
        if (prefs.getBoolean(MUSIC_PREFS, true)){
            isMusickOn.isMusicOn.value = true
        }
    }

    override fun onStop() {
        super.onStop()
        if(mMediaPlayer!=null){
            mMediaPlayer!!.release()
            mMediaPlayer=null
        }
    }

    private fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.base_music)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    private fun stopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    private fun observeMusic() {
        isMusickOn.isMusicOn.observe(this) {
            if (it) {
                playSound()
            }else{
                stopSound()
            }
        }
    }



}