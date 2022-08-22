package com.network.tafasir.UI.Fragments

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import androidx.fragment.app.Fragment
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.network.tafasir.R
import com.network.tafasir.UI.Adapters.Recycler.SoraIndexAdapter
import com.network.tafasir.UI.Communication.Communicat
import com.network.tafasir.UI.ViewModel.MainViewModel


class SowarIndexF : Fragment() {

    private  val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var sowarIndexCard: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.sowar_index_fragment, container, false)
        val paddingControl = PaddingControl(view)

        val context = view.context
        val recyclerView: RecyclerView = view.findViewById(R.id.sowarIndexRecycler)
        sowarIndexCard = view.findViewById(R.id.sowarIndexCard)

        sowarIndexCard.visibility = View.VISIBLE


        getSowar()
        mainViewModel.allSowar.observe(viewLifecycleOwner, Observer { list ->

            sowarIndexCard.visibility = View.GONE
            //
            val adapter = SoraIndexAdapter(context, list) { posision ->
                //
                val transaction = activity as Communicat

                setRecycleState(recyclerView.getLayoutManager()!!.onSaveInstanceState()!!)

                transaction.TransferData(list[posision].sora_number, 1, 0)


            }

            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter


            val state = getRecycleState()
            if(state != null){
                recyclerView.getLayoutManager()!!.onRestoreInstanceState(state)
            }

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
        //
        searchResult(context, recyclerView)


        return view
    }

    private fun getSowar() {
        if (mainViewModel.allSowar.value.isNullOrEmpty()) {
            mainViewModel.allSowar()
        }
    }

    fun getRecycleState() : Parcelable?{
        return mainViewModel.getSowarIndexRecycleState()
    }

    fun setRecycleState(state: Parcelable){
        mainViewModel.setSowarIndexRecycleState(state)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
    }

    fun searchResult(context: Context, recyclerView: RecyclerView){
        mainViewModel.soraSearch.observe(viewLifecycleOwner, Observer { list ->
            val adapter = SoraIndexAdapter(context, list) { posision ->
                //
                val transaction = activity as Communicat
                transaction.TransferData(list[posision].sora_number, 1, 0)
            }

            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
        })
    }
}