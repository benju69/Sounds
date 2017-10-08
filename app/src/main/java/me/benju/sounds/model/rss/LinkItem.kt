package me.benju.sounds.model.rss

import com.squareup.moshi.Json

data class LinkItem(
        @Json(name = "im:duration")
        val imDuration: ImDuration,
        val attributes: Attributes? = null
)
