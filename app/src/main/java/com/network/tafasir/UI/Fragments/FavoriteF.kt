package com.network.tafasir.UI.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
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

        val paddingControl = PaddingControl(view)

        val recyclerView:RecyclerView = view.findViewById(R.id.favoriteRecycler)

        favoriteViewModel.getAll()
        favoriteViewModel.allFavorite.observe(viewLifecycleOwner, Observer { list ->
            //
            val adapter = FavoriteAdapter(context, list)
            recyclerView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            recyclerView.adapter = adapter

            //Padding Control
            val listSize = adapter.itemCount
            recyclerView.addOnScrollListener(object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                    val layoutManager =
                        LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                    val position = layoutManager.findLastVisibleItemPosition() + 1


                    if (position == listSize || position > listSize) {
                        paddingControl.addPadding()
                    } else {
                        paddingControl.removePadding()
                    }

                }
            })
        })

        return view
    }




}