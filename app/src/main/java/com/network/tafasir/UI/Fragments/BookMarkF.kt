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
import com.network.tafasir.DATA.Database.DataClasses.BookMarkAyah
import com.network.tafasir.R
import com.network.tafasir.UI.Adapters.Recycler.BookmarkAdapter
import com.network.tafasir.UI.Adapters.Recycler.FavoriteAdapter
import com.network.tafasir.UI.Communication.Communicat
import com.network.tafasir.UI.ViewModel.BookMarkViewModel
import com.network.tafasir.UI.ViewModel.FavoriteViewModel


class BookMarkF : Fragment() {

    private val bookMarkViewModel : BookMarkViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_mark, container, false)
        val context = view.context
        val paddingControl = PaddingControl(view)

        val recyclerView: RecyclerView = view.findViewById(R.id.bookmarkRecycler)

        bookMarkViewModel.getAll()
        bookMarkViewModel.result.observe(viewLifecycleOwner, Observer {list ->
            //
            val adapter = BookmarkAdapter(context, list) { position ->
                openAyah(position, list)

            }
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter

            //Padding Control
            val listSize = adapter.itemCount
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

    private fun openAyah(posision: Int, list: List<BookMarkAyah>?) {
        val transaction = activity as Communicat
        transaction.TransferData(list!![posision].soraNumber, 1, list!![posision].ayahNumber)
    }

}