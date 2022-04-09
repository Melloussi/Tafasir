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
import com.network.tafasir.UI.Adapters.Recycler.FavoriteAdapter
import com.network.tafasir.UI.ViewModel.FavoriteViewModel



class FavoriteF : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        val context = view.context

        val recyclerView:RecyclerView = view.findViewById(R.id.favoriteRecycler)

        favoriteViewModel.getAll()
        favoriteViewModel.allFavorite.observe(viewLifecycleOwner, Observer {list ->
            //
            val adapter = FavoriteAdapter(context, list)
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
        })

        return view
    }

}