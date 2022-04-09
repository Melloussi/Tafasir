package com.network.tafasir.UI.Adapters.Recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.network.tafasir.DATA.Database.DataClasses.Ayah
import com.network.tafasir.R

class AyahtSuggestionAdapter(
    val context: Context,
    val list: List<Ayah>,
    val click: (position:Int, soraNum:Int, ayahNum:Int) -> Unit
) : RecyclerView.Adapter<AyahtSuggestionAdapter.MyHolder>(){

    inner class MyHolder(view: View):RecyclerView.ViewHolder(view) {
        val ayahSearchTv = view.findViewById<TextView>(R.id.ayahSearchTv)
        val ayahSearchCardView = view.findViewById<CardView>(R.id.ayahSearchCardView)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AyahtSuggestionAdapter.MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.ayah_search_model, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: AyahtSuggestionAdapter.MyHolder, position: Int) {
        holder.ayahSearchTv.text = list[position].ayah
        //
        holder.ayahSearchCardView.setOnClickListener {
            val soraNum = list[position].sora_number
            val ayahNum = list[position].ayah_number
            click(position, soraNum, ayahNum)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}