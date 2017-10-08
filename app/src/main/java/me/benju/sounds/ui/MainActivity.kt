package me.benju.sounds.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import bind
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.benju.sounds.R
import me.benju.sounds.api.MusicService
import me.benju.sounds.model.rss.TopSongs
import me.benju.sounds.model.search.SearchResult

class MainActivity : AppCompatActivity() {

    private val recyclerView by bind<RecyclerView>(R.id.list)

    val client = MusicService()

    // TODO search bar, search view
    // TODO country
    // TODO MVP
    // TODO Music player
    // TODO design

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //getMusic()

        getTop100()
    }

    fun getTop100() {
        client.api.getTop100ForSongs("FR")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    Log.d("top", result.toString())

                    setTopSongsList(result)
                }, {error ->
                    error.printStackTrace()
                })
    }

    private fun setTopSongsList(result: TopSongs) {
        var adapter = ChartsAdapter(this, result.feed!!.entry)
        recyclerView.adapter = adapter
    }

    fun getMusic(searchQuery: String) {
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
        var adapter = MusicAdapter(this, result.results)
        recyclerView.adapter = adapter
    }
}
