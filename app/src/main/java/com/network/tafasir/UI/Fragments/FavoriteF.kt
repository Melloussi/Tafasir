package com.network.tafasir.UI.Fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
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
import com.network.tafasir.UI.Controlers.ShareContent
import com.network.tafasir.UI.ViewModel.FavoriteViewModel
import com.network.tafasir.UI.ViewModel.MainViewModel


class FavoriteF : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private var adapter: FavoriteAdapter? = null
    private lateinit var tfsir:String
    private lateinit var mofsirName:String
    private var shareIt = ShareContent()
    private var isDefault = true


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

                },{ position, view ->

                    //Share section

                    //set Default Data
                    if (isDefault){
                        mofsirName = getString(R.string.moyasar)
                        tfsir = list[position].tafsir
                    }

                    val data = ShareContent.SharedData(list[position].soraName,
                        list[position].ayah,
                        list[position].sora_number,
                        list[position].ayah_number,
                        tfsir,
                        mofsirName)

                    shareIt.shareContent(data, view, context)

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

    private fun tafsirPopUpMenu(view: View, soraNumber: Int, ayahNumber: Int, position: Int)     {

        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.tafasir_menu)
        popup.setOnMenuItemClickListener { item ->


            when (item.itemId) {
                R.id.moyasar -> {

                    changeTafsir(soraNumber, ayahNumber, 1, position)
                    Toast.makeText(context, getString(R.string.moyasar), Toast.LENGTH_SHORT).show()

                }
                R.id.jalalain -> {

                    changeTafsir(soraNumber, ayahNumber, 2, position)
                    Toast.makeText(context, getString(R.string.jalalain), Toast.LENGTH_SHORT).show()


                }
                R.id.saidi -> {

                    changeTafsir(soraNumber, ayahNumber, 3, position)
                    Toast.makeText(context, getString(R.string.saidi), Toast.LENGTH_SHORT).show()


                }
                R.id.ibenKatir -> {

                    changeTafsir(soraNumber, ayahNumber, 4, position)
                    Toast.makeText(context, getString(R.string.ibenKatir), Toast.LENGTH_SHORT).show()

                }
                R.id.tentawi -> {

                    changeTafsir(soraNumber, ayahNumber, 5, position)
                    Toast.makeText(context, getString(R.string.tentawi), Toast.LENGTH_SHORT).show()

                }
                R.id.baghawi -> {

                    changeTafsir(soraNumber, ayahNumber, 6, position)
                    Toast.makeText(context, getString(R.string.baghawi), Toast.LENGTH_SHORT).show()

                }
                R.id.qurtobi -> {

                    changeTafsir(soraNumber, ayahNumber, 7, position)
                    Toast.makeText(context, getString(R.string.qurtobi), Toast.LENGTH_SHORT).show()

                }
                R.id.tabari -> {

                    changeTafsir(soraNumber, ayahNumber, 8, position)
                    Toast.makeText(context, getString(R.string.tabari), Toast.LENGTH_SHORT).show()

                }

            }

            false
        }

        popup.show()

    }

    private fun changeTafsir(soraNum:Int, ayahNum:Int, tafsirNum:Int, position:Int){

        mainViewModel.getTafsir(soraNum, ayahNum, tafsirNum){it ->
            tfsir = it.tafsir
            mofsirName = it.mofasir_name
            adapter!!.changeTafsir(it.tafsir, position)
            isDefault = false
        }
    }




}