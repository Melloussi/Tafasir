package com.network.tafasir.UI.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.network.tafasir.DATA.Database.DataClasses.SoraWithTafsir
import com.network.tafasir.DATA.Database.Room.Favorite.FavoriteEntity
import com.network.tafasir.R
import com.network.tafasir.UI.Adapters.Recycler.FavoriteAdapter
import com.network.tafasir.UI.ViewModel.FavoriteViewModel
import com.network.tafasir.UI.ViewModel.MainViewModel


class FavoriteF : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private var adapter: FavoriteAdapter? = null
    private var active = false


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

            adapter = FavoriteAdapter(context, list

                ,{ position ->

                    //Delete
                    favoriteViewModel.delete(list[position])
                    list.removeAt(position)
                    adapter?.notifyDataSetChanged()

                },{ position, view ->

                  //Tafsir
                    tafsirPopUpMenu(view, list[position].sora_number, list[position].ayah_number, position)

                    mainViewModel.tafsir.observe(viewLifecycleOwner, Observer { list ->

                        if (active) {
                            println("---- I got you: ${list.tafsir} ------")
                            adapter!!.changeTafsir(list.tafsir, position)
                            active = false
                        }

                    })


                },{ position, view ->
                    //Share
            })
            recyclerView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            recyclerView.adapter = adapter

            //Padding Control
            val listSize = adapter!!.itemCount
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

    private fun tafsirPopUpMenu(view: View, soraNumber: Int, ayahNumber: Int, position:Int)     {

        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.tafasir_menu)
        popup.setOnMenuItemClickListener { item ->


            when (item.itemId) {
                R.id.moyasar -> {

                    mainViewModel.tafsir(soraNumber, ayahNumber, 1)
                    Toast.makeText(context, getString(R.string.moyasar), Toast.LENGTH_SHORT).show()
                    active = true
                }
                R.id.jalalain -> {

                    mainViewModel.tafsir(soraNumber, ayahNumber, 2)
                    Toast.makeText(context, getString(R.string.jalalain), Toast.LENGTH_SHORT).show()
                    active = true

                }
                R.id.saidi -> {

                    mainViewModel.tafsir(soraNumber, ayahNumber, 3)
                    Toast.makeText(context, getString(R.string.saidi), Toast.LENGTH_SHORT).show()
                    active = true

                }
                R.id.ibenKatir -> {

                    mainViewModel.tafsir(soraNumber, ayahNumber, 4)
                    Toast.makeText(context, getString(R.string.ibenKatir), Toast.LENGTH_SHORT).show()
                    active = true
                }
                R.id.tentawi -> {

                    mainViewModel.tafsir(soraNumber, ayahNumber, 5)
                    Toast.makeText(context, getString(R.string.tentawi), Toast.LENGTH_SHORT).show()
                    active = true
                }
                R.id.baghawi -> {

                    mainViewModel.tafsir(soraNumber, ayahNumber, 6)
                    Toast.makeText(context, getString(R.string.baghawi), Toast.LENGTH_SHORT).show()
                    active = true
                }
                R.id.qurtobi -> {

                    mainViewModel.tafsir(soraNumber, ayahNumber, 7)
                    Toast.makeText(context, getString(R.string.qurtobi), Toast.LENGTH_SHORT).show()
                    active = true
                }
                R.id.tabari -> {

                    mainViewModel.tafsir(soraNumber, ayahNumber, 8)
                    Toast.makeText(context, getString(R.string.tabari), Toast.LENGTH_SHORT).show()
                    active = true
                }

            }

            false
        }

        popup.show()

    }


}