package me.benju.sounds.api

import io.reactivex.Observable
import me.benju.sounds.model.rss.TopSongs
import me.benju.sounds.model.search.SearchResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by benju on 07/10/2017.
 */
interface Api {

    @GET("{country}/rss/topsongs/limit=100/json") // topsongs and limit can be changed
    fun getTop100ForSongs(
            @Path("country") country: String): Observable<TopSongs>

    @GET("search")
    fun search(
            @Query("term") term: String,
            @Query("country") country: String,
            @Query("limit") limit: Int,
            @Query("media") media: String): Observable<SearchResult>

}