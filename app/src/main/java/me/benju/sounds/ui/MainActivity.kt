package me.benju.sounds.ui

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.*
import bind
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import loadImg
import me.benju.sounds.R
import me.benju.sounds.api.MusicService
import me.benju.sounds.model.rss.TopSongs
import me.benju.sounds.model.search.SearchResult
import java.io.IOException



class MainActivity : AppCompatActivity() {

    private val recyclerView by bind<RecyclerView>(R.id.list)
    private val artistName by bind<TextView>(R.id.nowPlayingArtistName)
    private val trackName by bind<TextView>(R.id.nowPlayingTrackName)
    private val albumArt by bind<ImageView>(R.id.nowPlayingAlbumArt)
    private val playPauseButton by bind<ImageButton>(R.id.imageButtonPlayPauseNote)
    private val seekBar by bind<SeekBar>(R.id.seekBar)

    private val progressBar by bind<ProgressBar>(R.id.progressBar)
    private val searchEditText by bind<EditText>(R.id.edit_text_search)

    private val client = MusicService()
    private val mediaPlayer = MediaPlayer()
    private val seekHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getTop100()

        RxTextView.textChanges(searchEditText)
                .subscribe { value: CharSequence ->
                    searchMusic(value.toString().trim())
                }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.release()
    }

    fun getTop100() {
        client.api.getTop100ForSongs("FR")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    Log.d("top", result.toString())

                    hideProgress()
                    setTopSongsList(result)
                }, {error ->
                    error.printStackTrace()
                })
    }

    private fun setTopSongsList(result: TopSongs) {
        recyclerView.adapter = ChartsAdapter(result.feed!!.entry) {
            val url = it.link?.get(1)?.attributes?.href
            Log.i("url", url)
            setPlayer(url)

            albumArt.loadImg(it.imImage?.get(2)?.label!!)
            trackName.text = it.imName!!.label
            artistName.text = it.imArtist!!.label
        }
    }

    private fun setPlayer(url: String?) {
        mediaPlayer.stop()
        mediaPlayer.reset()

        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare() // might take long! (for buffering, etc)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {
                mediaPlayer.pause()
                mediaPlayer.stop()
                mediaPlayer.reset()
            }
        } catch (exception : IOException) {

        }

        seekBar.max = mediaPlayer.duration
        seekUpdater()

        playPauseButton.setImageResource(R.drawable.ic_pause_black_48dp)

        playPauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                playPauseButton.setImageResource(R.drawable.ic_play_arrow_black_48dp)
            } else {
                mediaPlayer.start()
                playPauseButton.setImageResource(R.drawable.ic_pause_black_48dp)
            }
        }
    }

    fun searchMusic(searchQuery: String) {
        client.api.search(searchQuery, "FR", 100, "music")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    Log.d("music", result.toString())

                    setMusicList(result)
                }, { error ->
                    error.printStackTrace()
                })

    }

    private fun setMusicList(result: SearchResult) {
        recyclerView.adapter = MusicAdapter(result.results) {
            val url = it.previewUrl
            Log.i("url", url)
            setPlayer(url)

            albumArt.loadImg(it.artworkUrl100!!)
            trackName.text = it.trackName
            artistName.text = it.artistName
        }
    }

    fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    fun seekUpdater() {
        if (mediaPlayer != null) {
            seekBar.progress = mediaPlayer.currentPosition
            seekHandler.postDelayed(runnableSeek, 100)
        }
    }

    var runnableSeek: Runnable = Runnable { seekUpdater() }
}
