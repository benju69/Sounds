package me.benju.sounds.model.rss

data class Feed(
	val entry: List<EntryItem>,
	val author: Author? = null,
	val rights: Rights? = null,
	val icon: Icon? = null,
	val link: List<LinkItem?>? = null,
	val id: Id? = null,
	val title: Title? = null,
	val updated: Updated? = null
)
