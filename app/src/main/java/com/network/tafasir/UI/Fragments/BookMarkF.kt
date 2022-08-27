package com.network.tafasir.UI.Fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.network.tafasir.DATA.Database.DataClasses.BookMarkAyah
import com.network.tafasir.DATA.Database.Room.BookMark.BookMarkEntity
import com.network.tafasir.R
import com.network.tafasir.UI.Adapters.Recycler.BookmarkAdapter
import com.network.tafasir.UI.Adapters.Recycler.FavoriteAdapter
import com.network.tafasir.UI.Communication.Communicat
import com.network.tafasir.UI.ViewModel.BookMarkViewModel
import com.network.tafasir.UI.ViewModel.FavoriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class BookMarkF : Fragment() {

    private val bookMarkViewModel: BookMarkViewModel by activityViewModels()
    private var isListEmpty = true
    private lateinit var bookMarkDefaultMessageTv: TextView
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_mark, container, false)
        val context = view.context
        val paddingControl = PaddingControl(view)
        var adapter: BookmarkAdapter? = null

        bookMarkDefaultMessageTv = view.findViewById<TextView>(R.id.bookMarkDefaultMessageTv)
        recyclerView = view.findViewById(R.id.bookmarkRecycler)

        bookMarkViewModel.getAll() // Get Data
        bookMarkViewModel.result.observe(viewLifecycleOwner, Observer { list ->
            //

            if (!list.isEmpty()) {
                isListEmpty = false
                hideDefaultMessage()
            }

            adapter = BookmarkAdapter(context, list, { position ->
                openAyah(position, list)


            }, { position ->

                //Alert Dialog To Confirm Deleting Selected BookMark

                val builder = AlertDialog.Builder(context)

                builder.setMessage(getString(R.string.areYouSureFromDeleteAction))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes)) { dialog, id ->

                        // Delete selected
                        bookMarkViewModel.delete(list[position])
                        list.removeAt(position)
                        adapter?.notifyDataSetChanged()

                    }
                    .setNegativeButton(getString(R.string.no)) { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }

                val alert = builder.create()
                alert.show()
            }, {
                //Empty Adapter Listener
                if(list.isEmpty()){
                    isListEmpty = true
                    hideDefaultMessage()
                }else{
                    isListEmpty = false
                    hideDefaultMessage()
                }
            })



            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter

            //Padding Control
            val listSize = adapter?.itemCount
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                    val layoutManager =
                        LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                    val position = layoutManager.findLastVisibleItemPosition() + 1


                    if (position == listSize || position > listSize!!) {
                        paddingControl.addPadding()
                    } else {
                        paddingControl.removePadding()
                    }

                }
            })
        })

        return view
    }


    private fun openAyah(posision: Int, list: List<BookMarkEntity>?) {
        val transaction = activity as Communicat
        transaction.TransferData(list!![posision].soraNumber, 1, list!![posision].ayahNumber)
    }

    private fun hideDefaultMessage() {

        if (isListEmpty) {
            recyclerView.visibility = View.GONE
            bookMarkDefaultMessageTv.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            bookMarkDefaultMessageTv.visibility = View.GONE
        }
    }

}