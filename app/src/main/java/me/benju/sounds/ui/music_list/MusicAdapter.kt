package me.benju.sounds.ui.music_list

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import bind
import inflate
import loadImg
import me.benju.sounds.R
import me.benju.sounds.model.search.ResultsItem

/**
 * Created by benju on 07/10/2017.
 */
class MusicAdapter(
        private var data: List<ResultsItem>,
        private val clickListener: (ResultsItem) -> Unit
) : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resultEntry = data[position]

        holder.art.loadImg(resultEntry.artworkUrl100!!)
        holder.title.text = resultEntry.trackName
        holder.artist.text = resultEntry.artistName
        holder.card.setOnClickListener { clickListener(resultEntry) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_music, false))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card by itemView.bind<CardView>(R.id.card)
        val art by itemView.bind<ImageView>(R.id.albumArt)
        val title by itemView.bind<TextView>(R.id.trackName)
        val artist by itemView.bind<TextView>(R.id.artistName)
    }

}