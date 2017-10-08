package me.benju.sounds.ui.music_list

import me.benju.sounds.model.rss.TopSongs
import me.benju.sounds.model.search.SearchResult
import me.benju.sounds.ui.BasePresenter
import me.benju.sounds.ui.BaseView

/**
 * Created by benju on 08/10/2017.
 */
interface MusicListContract {
    interface View : BaseView<Presenter> {
        fun setTopSongsList(result: TopSongs)
        fun setSearchResultsList(result: SearchResult)
        fun showErrorMessage()
        fun hideProgress()
        fun showProgress()
    }

    interface Presenter : BasePresenter {
        fun getTop100()
        fun searchMusic(searchQuery: String)
    }
}