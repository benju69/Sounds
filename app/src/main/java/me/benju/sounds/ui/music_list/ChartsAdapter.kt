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
import me.benju.sounds.model.rss.EntryItem

/**
 * Created by benju on 07/10/2017.
 */
class ChartsAdapter(
        private var data: List<EntryItem>,
        private val clickListener: (EntryItem) -> Unit
) : RecyclerView.Adapter<ChartsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = data[position]

        val imageUrl = entry.imImage?.get(1)?.label
        holder.art.loadImg(imageUrl!!)
        holder.title.text = entry.imName!!.label
        holder.artist.text = entry.imArtist!!.label
        holder.card.setOnClickListener { clickListener(entry) }
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