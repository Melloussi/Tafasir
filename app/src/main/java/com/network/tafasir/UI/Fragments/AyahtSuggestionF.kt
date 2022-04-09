package com.network.tafasir.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.network.tafasir.R
import com.network.tafasir.UI.Adapters.Recycler.AyahtSuggestionAdapter
import com.network.tafasir.UI.Communication.Communicat
import com.network.tafasir.UI.ViewModel.MainViewModel

class AyahtSuggestionF : Fragment() {

    private  val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_ayaht_suggestion, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.ayahtSuggestionRecycle)
        val context = view.context
        val transaction = activity as Communicat

        mainViewModel.ayahSearch.observe(viewLifecycleOwner, Observer { list ->
            //
            val adapter = AyahtSuggestionAdapter(context, list){ position, soraNum, ayahNum ->
                //
                transaction.TransferData(list[position].sora_number, 1, list[position].ayah_number)
            }
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
        })


        return view
    }
}