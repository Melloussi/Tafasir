package com.network.tafasir.UI.Adapters.Recycler

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.network.tafasir.DATA.Database.DataClasses.BookMarkAyah
import com.network.tafasir.DATA.Database.Room.BookMark.BookMarkEntity
import com.network.tafasir.R

class BookmarkAdapter(val context: Context, val list: MutableList<BookMarkEntity>, val cardClicked:(position:Int)-> Unit, val bookMarkIconClicked:(position:Int)-> Unit) : RecyclerView.Adapter<BookmarkAdapter.Myholder>() {

    inner class Myholder (view: View) : RecyclerView.ViewHolder(view) {
        val soraNameTv = view.findViewById<TextView>(R.id.soraNameTv)
        val ayahNumberTv = view.findViewById<TextView>(R.id.ayahNumberTv)
        val bookMarkCard = view.findViewById<CardView>(R.id.bookMarkCard)
        val bookMarkIcon = view.findViewById<ImageView>(R.id.bookMarkIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkAdapter.Myholder {
        val view = LayoutInflater.from(context).inflate(R.layout.ayah_bookmark_model, parent, false)
        return Myholder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Myholder, position: Int) {
        val text = context.resources.getText(R.string.ayah)
        val ayahNumber = list[position].ayahNumber.toString()

        holder.soraNameTv.text = list[position].soraName
        holder.ayahNumberTv.text = "$text $ayahNumber"

        holder.bookMarkCard.setOnClickListener(View.OnClickListener {
            cardClicked(position)
        })
        holder.bookMarkIcon.setOnClickListener(View.OnClickListener {
            bookMarkIconClicked(position)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }
}