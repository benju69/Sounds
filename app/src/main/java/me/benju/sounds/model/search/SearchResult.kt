package me.benju.sounds.model.search

data class SearchResult(
        val resultCount: Int? = null,
        val results: List<ResultsItem>
)