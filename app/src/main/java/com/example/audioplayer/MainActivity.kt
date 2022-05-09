package com.example.audioplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var seekBar: SeekBar
    private  var mediaPlayer: MediaPlayer?=null
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seekBar=findViewById(R.id.seekBar)
        handler= Handler(Looper.getMainLooper())
        val play = findViewById<FloatingActionButton>(R.id.floatingplay)
        val pause = findViewById<FloatingActionButton>(R.id.floatingpause)
        val stop = findViewById<FloatingActionButton>(R.id.floatingstop)

        play.setOnClickListener{
            if(mediaPlayer==null)
            {
                mediaPlayer = MediaPlayer.create(this,R.raw.yearning)
                seekbarinit()

            }
            mediaPlayer?.start()
        }
        pause.setOnClickListener{
            mediaPlayer?.pause()
        }

        stop.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()

            mediaPlayer = null
            handler.removeCallbacks(runnable)
            seekBar.progress=0

        }



    }
    private fun seekbarinit(){
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
             if(fromUser)mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
        val tvplayed = findViewById<TextView>(R.id.tvplay)
        val tvdue = findViewById<TextView>(R.id.tvstop)
          seekBar.max=mediaPlayer!!.duration

        runnable = Runnable {
         seekBar.progress=mediaPlayer!!.currentPosition

            val playtime = mediaPlayer!!.currentPosition/1000
            tvplayed.text=("$playtime sec")
            val duetime = mediaPlayer!!.duration/1000
            val due = duetime-playtime
            tvdue.text=("$due sec ")
            handler.postDelayed(runnable,1000)
        }
        handler.postDelayed(runnable,1000)
    }
}