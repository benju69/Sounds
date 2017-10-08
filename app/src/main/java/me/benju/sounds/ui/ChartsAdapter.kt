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
import me.benju.sounds.model.rss.EntryItem

/**
 * Created by benju on 07/10/2017.
 */
class ChartsAdapter(
        private var context: Activity,
        private var data: List<EntryItem>
) : RecyclerView.Adapter<ChartsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = data[position]

        val imageUrl = entry.imImage?.get(0)?.label
        holder.art.loadImg(imageUrl!!)
        holder.title.text = entry.title!!.label
        holder.artist.text = entry.imArtist!!.label
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(parent.inflate(R.layout.item_music, false))
        return viewHolder.listen { position, _ ->
            // TODO open, play music in a new view, or in the same view, make a player

            // simple mediaplayer
//            val url = data[position].previewUrl
//            val mediaPlayer = MediaPlayer()
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
//            mediaPlayer.setDataSource(url)
//            mediaPlayer.prepare() // might take long! (for buffering, etc)
//            mediaPlayer.start()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val art by itemView.bind<ImageView>(R.id.albumArt)
        val title by itemView.bind<TextView>(R.id.trackName)
        val artist by itemView.bind<TextView>(R.id.artistName)
    }

}