package me.benju.sounds.ui.music_list

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
import loadImg
import me.benju.sounds.R
import me.benju.sounds.model.rss.TopSongs
import me.benju.sounds.model.search.SearchResult
import java.io.IOException

class MusicListActivity : AppCompatActivity(), MusicListContract.View {

    private val recyclerView by bind<RecyclerView>(R.id.list)
    private val artistName by bind<TextView>(R.id.nowPlayingArtistName)
    private val trackName by bind<TextView>(R.id.nowPlayingTrackName)
    private val albumArt by bind<ImageView>(R.id.nowPlayingAlbumArt)
    private val playPauseButton by bind<ImageButton>(R.id.imageButtonPlayPauseNote)
    private val seekBar by bind<SeekBar>(R.id.seekBar)

    private val progressBar by bind<ProgressBar>(R.id.progressBar)
    private val searchEditText by bind<EditText>(R.id.edit_text_search)

    private lateinit var mediaPlayer: MediaPlayer
    private val seekHandler = Handler()

    private var url: String? = null

    private lateinit var presenter: MusicListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MusicListPresenter(this)
        presenter.start()

        RxTextView.textChanges(searchEditText)
                .subscribe { value: CharSequence ->
                    if (!value.isEmpty()) {
                        presenter.searchMusic(value.toString().trim())
                    } else {
                        presenter.getTop100()
                    }
                }
    }

    override fun onResume() {
        mediaPlayer = MediaPlayer()
        if (url != null) {
           setPlayer(url)
        }
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        super.onPause()
        seekHandler.removeCallbacks(runnableSeek)
        mediaPlayer?.release()
    }

    override fun setPresenter(presenter: MusicListContract.Presenter) {
        this.presenter = presenter
    }

    override fun showErrorMessage() {
        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun setTopSongsList(result: TopSongs) {
        recyclerView.adapter = ChartsAdapter(result.feed!!.entry) {
            url = it.link?.get(1)?.attributes?.href
            setPlayer(url)

            albumArt.loadImg(it.imImage?.get(2)?.label!!)
            trackName.text = it.imName!!.label
            artistName.text = it.imArtist!!.label
        }
    }

    override fun setSearchResultsList(result: SearchResult) {
        recyclerView.adapter = MusicAdapter(result.results) {
            url = it.previewUrl
            setPlayer(url)

            albumArt.loadImg(it.artworkUrl100!!)
            trackName.text = it.trackName
            artistName.text = it.artistName
        }
    }

    private fun setPlayer(url: String?) {
        Log.i("url", url)

        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare() // might take long! (for buffering, etc)
            mediaPlayer.start()
            playPauseButton.setImageResource(R.drawable.ic_pause_black_48dp)
            mediaPlayer.setOnCompletionListener {
                playPauseButton.setImageResource(R.drawable.ic_play_arrow_black_48dp)
                setPlayButton()
            }
        } catch (exception : IOException) {
        }

        seekBar.max = mediaPlayer.duration
        updateSeekBar()

        setPlayButton()
    }

    private fun setPlayButton() {
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

    private fun updateSeekBar() {
        if (mediaPlayer != null) {
            seekBar.progress = mediaPlayer.currentPosition
            seekHandler.postDelayed(runnableSeek, 100)
        }
    }

    var runnableSeek: Runnable = Runnable { updateSeekBar() }
}
