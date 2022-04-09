package com.network.tafasir.UI.Adapters.Recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.network.tafasir.DATA.Database.DataClasses.Sowara
import com.network.tafasir.R
import com.network.tafasir.UI.Activities.MainActivity
import com.network.tafasir.UI.Communication.Communicat

class SoraIndexAdapter(val context: Context, val list: List<Sowara>, val cardClicked:(position:Int)-> Unit ): RecyclerView.Adapter<SoraIndexAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View):RecyclerView.ViewHolder(view){

        val soraNumberTv = view.findViewById<TextView>(R.id.soraNumberTv)
        val soraTypeTv = view.findViewById<TextView>(R.id.soraTypeTv)
        val ayahtNumberTv = view.findViewById<TextView>(R.id.ayahtNumberTv)
        val soraNameTv = view.findViewById<TextView>(R.id.soraNameTv)
        val sowraIndexCard = view.findViewById<CardView>(R.id.sowraIndexCard)


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SoraIndexAdapter.MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sowra_index_model, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: SoraIndexAdapter.MyViewHolder, position: Int) {

        holder.soraNameTv.text = list[position].sora_name
        holder.ayahtNumberTv.text = list[position].ayat_number.toString()
        holder.soraTypeTv.text = list[position].sora_type
        holder.soraNumberTv.text = list[position].sora_number.toString()

        holder.sowraIndexCard.setOnClickListener(View.OnClickListener {
            cardClicked(position)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

}