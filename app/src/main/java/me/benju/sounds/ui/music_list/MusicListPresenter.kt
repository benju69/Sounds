package me.benju.sounds.ui.music_list

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.benju.sounds.api.MusicService
import java.util.*

/**
 * Created by benju on 08/10/2017.
 */
class MusicListPresenter (
        val view: MusicListContract.View
) : MusicListContract.Presenter {

    private val client = MusicService()
    private val locale = Locale.getDefault().country

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
        getTop100()
    }

    override fun getTop100() {
        view.showProgress()
        client.api.getTop100ForSongs(locale)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    Log.d("top", result.toString())
                    view.hideProgress()
                    view.setTopSongsList(result)
                }, {error ->
                    error.printStackTrace()
                    view.showErrorMessage()
                })
    }

    override fun searchMusic(searchQuery: String) {
        view.showProgress()
        client.api.search(searchQuery, locale, 100, "music")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    Log.d("search", result.toString())
                    view.hideProgress()
                    view.setSearchResultsList(result)
                }, { error ->
                    error.printStackTrace()
                    view.showErrorMessage()
                })
    }

}