package com.network.tafasir.UI.Adapters.Recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.network.tafasir.DATA.Database.DataClasses.SoraWithTafsir
import com.network.tafasir.R

class ReadAdapter
    (
    val context: Context,
    val list: List<SoraWithTafsir>,
    val shareAyah: (position:Int, view:View) -> Unit,
    val tafsirAyah: (position:Int, view:View) -> Unit,
    val favoriteAyah: (position:Int) -> Unit,
    val bookmarkAyah: (position:Int) -> Unit
)
    : RecyclerView.Adapter<ReadAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val ayahNumberTv = view.findViewById<TextView>(R.id.ayahNumberTv)
        val ayahTv = view.findViewById<TextView>(R.id.ayahTv)
        val tafsirTv = view.findViewById<TextView>(R.id.tafsirTv)
        val shareIcon = view.findViewById<ImageView>(R.id.shareIcon)
        val tafsirIcon = view.findViewById<ImageView>(R.id.tafsirIcon)
        val heartBorderIcon = view.findViewById<ImageView>(R.id.heartBorderIcon)
        val bookMarkBorderIcon = view.findViewById<ImageView>(R.id.bookMarkBorderIcon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadAdapter.MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.read_ayah_model, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReadAdapter.MyViewHolder, position: Int) {
        val versNumber = position + 1
        holder.ayahNumberTv.text = versNumber.toString()
        holder.ayahTv.text = list[position].ayah
        holder.tafsirTv.text = list[position].tafsir

        holder.shareIcon.setOnClickListener(View.OnClickListener {
            shareAyah(position, holder.shareIcon)
        })
        holder.tafsirIcon.setOnClickListener(View.OnClickListener {
            tafsirAyah(position, holder.tafsirIcon)
        })
        holder.heartBorderIcon.setOnClickListener(View.OnClickListener{
            favoriteAyah(position)
            holder.heartBorderIcon.setImageResource(R.drawable.ic_favorite_)
        })
        holder.bookMarkBorderIcon.setOnClickListener(View.OnClickListener{
            bookmarkAyah(position)
            holder.bookMarkBorderIcon.setImageResource(R.drawable.bookmark_24)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }
}