package me.benju.sounds.ui

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import bind
import inflate
import listen
import loadImg
import me.benju.sounds.R
import me.benju.sounds.model.ResultsItem

/**
 * Created by benju on 07/10/2017.
 */
class MusicAdapter(
        private var context: Activity,
        private var data: List<ResultsItem>
) : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val music = data[position]

        holder.art.loadImg(music.artworkUrl100!!)
        holder.title.text = music.trackName
        holder.artist.text = music.artistName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(parent.inflate(R.layout.item_music, false))
        return viewHolder.listen { position, _ ->
            // TODO open, play music
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val art by itemView.bind<ImageView>(R.id.album_art)
        val title by itemView.bind<TextView>(R.id.title)
        val artist by itemView.bind<TextView>(R.id.artist)
    }

}