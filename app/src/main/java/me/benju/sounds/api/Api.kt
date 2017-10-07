package me.benju.sounds.api

import io.reactivex.Observable
import me.benju.sounds.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by benju on 07/10/2017.
 */
interface Api {

    @GET("search")
    fun getMusic(
            @Query("term") term: String,
            @Query("country") country: String,
            @Query("limit") limit: Int,
            @Query("media") media: String): Observable<Response>

}