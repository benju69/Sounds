package me.benju.sounds.model.rss

import com.squareup.moshi.Json

data class Attributes(
        val scheme: String? = null,
        val term: String? = null,
        val imId: String? = null,
        val label: String? = null,
        val title: String,
        val rel: String,
        val type: String,
        val href: String,
        @Json(name = "im:assetType")
        val imAssetType: String
)
