package me.benju.sounds.model

data class Response(
	val resultCount: Int? = null,
	val results: List<ResultsItem>
)
