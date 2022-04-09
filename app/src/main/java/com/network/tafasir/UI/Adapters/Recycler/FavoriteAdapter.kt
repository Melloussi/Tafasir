package com.network.tafasir.UI.Adapters.Recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.network.tafasir.DATA.Database.DataClasses.FavouriteAyah
import com.network.tafasir.R

class FavoriteAdapter(val context: Context, val list: List<FavouriteAyah>) : RecyclerView.Adapter<FavoriteAdapter.MyHolder>() {

    inner class MyHolder(view: View):RecyclerView.ViewHolder(view){
        val ayahNumberTv = view.findViewById<TextView>(R.id.ayahNumberTv)
        val soraNameTv = view.findViewById<TextView>(R.id.soraNameTv)
        val ayahTv = view.findViewById<TextView>(R.id.ayahTv)
        val tafsirTv = view.findViewById<TextView>(R.id.tafsirTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.ayah_favorite_model, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.MyHolder, position: Int) {
        holder.ayahNumberTv.text = list[position].ayah_number.toString()
        holder.soraNameTv.text = list[position].soraName
        holder.ayahTv.text = list[position].ayah
        holder.tafsirTv.text = list[position].tafsir
    }

    override fun getItemCount(): Int {
        return list.size
    }
}