package com.network.tafasir.UI.Adapters.Recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.network.tafasir.DATA.Database.DataClasses.FavouriteAyah
import com.network.tafasir.DATA.Database.Room.Favorite.FavoriteEntity
import com.network.tafasir.R

class FavoriteAdapter(val context: Context, val list: MutableList<FavoriteEntity>,
                      val removeIcon: (position:Int) -> Unit,
                      val tafsirAyah: (position:Int, view:View) -> Unit,
                      val shareAyah: (position:Int, view:View) -> Unit,
                      val thereIsNoItem: () -> Unit

                    ) : RecyclerView.Adapter<FavoriteAdapter.MyHolder>() {

    inner class MyHolder(view: View):RecyclerView.ViewHolder(view){


        val ayahNumberTv: TextView = view.findViewById<TextView>(R.id.ayahNumberTv)
        val soraNameTv: TextView = view.findViewById<TextView>(R.id.soraNameTv)
        val ayahTv: TextView = view.findViewById<TextView>(R.id.ayahTv)
        val tafsirTv: TextView = view.findViewById<TextView>(R.id.tafsirTv)
        val removeIcon: ImageView = view.findViewById<ImageView>(R.id.removeIcon)
        val tafsirIcon: ImageView = view.findViewById<ImageView>(R.id.tafsirIcon)
        val shareIcon: ImageView = view.findViewById<ImageView>(R.id.shareIcon)

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

        if (list.isEmpty()){

        }

        /* Listeners */
        holder.removeIcon.setOnClickListener(View.OnClickListener {
            removeIcon(position)
        })

        holder.tafsirIcon.setOnClickListener(View.OnClickListener {
            println("---- Tafsir Clicked, Position: $position ------")
            tafsirAyah(position, holder.tafsirIcon)
        })

        holder.shareIcon.setOnClickListener(View.OnClickListener {
            shareAyah(position, holder.shareIcon)
        })
    }

    override fun getItemCount(): Int {
        if (list.size == 0 || list.isEmpty()) {
            thereIsNoItem()
        }
        return list.size
    }

    fun changeTafsir(tafsir:String, position:Int){

        //New Object With the new Tafsir to add it to the list
        val changeTafsir = FavoriteEntity(list[position].id, list[position].ayah_number, list[position].sora_number, list[position].soraName, list[position].ayah, tafsir, true)

        list.removeAt(position)
        list.add(position, changeTafsir)

        this.notifyDataSetChanged()
    }

}