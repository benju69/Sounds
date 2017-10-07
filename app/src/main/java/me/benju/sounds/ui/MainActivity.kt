package me.benju.sounds.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.util.Log
import bind
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.benju.sounds.R
import me.benju.sounds.api.MusicService
import me.benju.sounds.model.Response

class MainActivity : AppCompatActivity() {

    private val recyclerView by bind<RecyclerView>(R.id.list)

    val client = MusicService()

    // TODO search bar
    // TODO country
    // TODO MVP
    // TODO Music player
    // TODO design

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpRecyclerView()

        getMusic()
    }

    private fun setUpRecyclerView() {
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
    }

    fun getMusic() {
        client.api.getMusic("top", "FR", 100, "music")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    Log.d("music", result.toString())

                    setMusicList(result)
                }, { error ->
                    error.printStackTrace()
                })

    }

    private fun setMusicList(result: Response) {
        var adapter = MusicAdapter(this, result.results)
        recyclerView.adapter = adapter
    }
}
