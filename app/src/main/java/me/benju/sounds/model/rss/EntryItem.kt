package me.benju.sounds.model.rss

import com.squareup.moshi.Json

data class EntryItem(
        @Json(name = "im:artist") val imArtist: ImArtist? = null,
        @Json(name = "im:name") val imName: ImName? = null,
        @Json(name = "im:contentType") val imContentType: ImContentType? = null,
        @Json(name = "im:image") val imImage: List<ImImageItem?>? = null,
        @Json(name = "im:collection") val imCollection: ImCollection? = null,
        val rights: Rights? = null,
        @Json(name = "im:price") val imPrice: ImPrice? = null,
        val link: List<LinkItem?>? = null,
        val id: Id? = null,
        val title: Title? = null,
        val category: Category? = null,
        @Json(name = "im:releaseDate") val imReleaseDate: ImReleaseDate? = null
)