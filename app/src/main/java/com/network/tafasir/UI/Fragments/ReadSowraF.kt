package com.network.tafasir.UI.Fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.network.tafasir.DATA.Database.DataClasses.FavouriteAyah
import com.network.tafasir.DATA.Database.DataClasses.SoraWithTafsir
import com.network.tafasir.DATA.Database.Room.BookMark.BookMarkEntity
import com.network.tafasir.DATA.Database.Room.Favorite.FavoriteEntity
import com.network.tafasir.R
import com.network.tafasir.UI.Adapters.Recycler.ReadAdapter
import com.network.tafasir.UI.Controlers.ShareContent
import com.network.tafasir.UI.ViewModel.BookMarkViewModel
import com.network.tafasir.UI.ViewModel.FavoriteViewModel
import com.network.tafasir.UI.ViewModel.MainViewModel

class ReadSowraF : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()
    private val bookmarkViewModel: BookMarkViewModel by activityViewModels()
    private lateinit var readAyahCard: CardView
    private lateinit var readSowraAppBar: AppBarLayout
    private lateinit var expandableLL: LinearLayout
    private lateinit var contentContainer: RelativeLayout
    private lateinit var aboutSowraTv: TextView
    private lateinit var soraNameTv: TextView
    private lateinit var soraTypeTv: TextView
    private lateinit var prostrationTv: TextView
    private lateinit var ayahtNumberTv: TextView
    private lateinit var titleOfAboutSowraTv: TextView
    private lateinit var soraOrderNumberTv: TextView
    private lateinit var expandImg: ImageView
    private lateinit var collapsingToolBar: AppBarLayout
    private var expandableLayout = false
    private var recycleCopy: RecyclerView? = null
    private var gloabaleList: List<SoraWithTafsir>? = null
    var _sowraNum = 0
    var _state: Parcelable? = null
    private lateinit var mofsirName:String
    private lateinit var tfsir:String
    private var shareIt = ShareContent()
    private var isDefault = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("--------- Fragment Create View ----------")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.read_sowra_fragment, container, false)

        val context = view.context
        readSowraAppBar = view.findViewById(R.id.appBar)
        readAyahCard = view.findViewById(R.id.readAyahCard)
        expandableLL = view.findViewById(R.id.expandableLL)
        contentContainer = view.findViewById(R.id.contentContainer)
        aboutSowraTv = view.findViewById(R.id.aboutSowraTv)
        expandImg = view.findViewById(R.id.expandImg)
        soraNameTv = view.findViewById(R.id.soraNameTv)
        ayahtNumberTv = view.findViewById(R.id.ayahtNumberTv)
        soraTypeTv = view.findViewById(R.id.soraTypeTv)
        soraOrderNumberTv = view.findViewById(R.id.soraOrderNumberTv)
        prostrationTv = view.findViewById(R.id.prostrationTv)
        collapsingToolBar = view.findViewById(R.id.appBar)
        titleOfAboutSowraTv = view.findViewById(R.id.titleOfAboutSowraTv)


        readSowraAppBar.visibility = View.GONE
        readAyahCard.visibility = View.VISIBLE

        val recyclerVieww: RecyclerView = view.findViewById(R.id.readRecycler)


        //mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val soraNum = arguments?.getInt("soraNum")
        val ayahNum = arguments?.getInt("ayahNum")

        if (soraNum != null) {
            _sowraNum = soraNum
        }


        if (soraNum != null) {
            mainViewModel.soraWithTafsir(soraNum, 1)
            mainViewModel.aboutSowra(soraNum)

            mainViewModel.soraWithTafsir.observe(viewLifecycleOwner, Observer { list ->
                //


                var prostration = ""
                if (list[0].prostration) {
                    prostration = getString(R.string.yes)
                } else {
                    prostration = getString(R.string.no)
                }

                titleOfAboutSowraTv.text = "${getString(R.string.aboutSora)} ${list[0].soraName}"
                soraNameTv.text = "${getString(R.string.soraNametext)} ${list[0].soraName}"
                soraTypeTv.text = list[0].soraType
                prostrationTv.text = "${getString(R.string.prostrationText)} $prostration"
                ayahtNumberTv.text = "${getString(R.string.ayahtNumberText)} ${list[0].ayat_number}"
                soraOrderNumberTv.text =
                    "${getString(R.string.soraOrderNumberText)} ${list[0].order_number}"


                readAyahCard.visibility = View.GONE
                readSowraAppBar.visibility = View.VISIBLE

                val adapter = ReadAdapter(context, list,
                    { position, view ->
                        //Share
//                        shareContent(list, position, view, context)
//
                        //set Default Data
                        if (isDefault){
                            mofsirName = getString(R.string.moyasar)
                            tfsir = list[position].tafsir
                        }

                        val data = ShareContent.SharedData(list[position].soraName,
                            list[position].ayah,
                            _sowraNum,
                            position+1,
                            tfsir,
                            mofsirName)

                        shareIt.shareContent(data, view, context)
                    },
                    { position, view ->

                        //ChangeTafsir
                        tafsirPopUpMenu(list, soraNum, position + 1, view, context)

                    },
                    {position ->

                        //Add To Favorite
                        addToFavorite(position, list)
                    },
                    {
                        //Save it as a BookMark
                        position -> saveBookmark(position, list[position].soraName, soraNum)
                    })

                recyclerVieww.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                recyclerVieww.adapter = adapter

                val state = mainViewModel.getAyahRecycleState()
                if (state != null && state.soraNum == soraNum) {

                    collapsingToolBar.setExpanded(false)
                    recyclerVieww.layoutManager!!.onRestoreInstanceState(state.viewState)
                }

                if (ayahNum != null && ayahNum != 0) {
                    recyclerVieww.scrollToPosition(ayahNum - 1!!)
                    collapsingToolBar.setExpanded(false)
                }
                recycleCopy = recyclerVieww

                recyclerVieww.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)

                        _state = recyclerView.getLayoutManager()!!.onSaveInstanceState()
                    }
                })


            })

            mainViewModel.aboutSowra.observe(viewLifecycleOwner, Observer {
                aboutSowraTv.text = HtmlCompat.fromHtml(it.about, HtmlCompat.FROM_HTML_MODE_LEGACY)
            })

        }

        expandableLL.setOnClickListener(View.OnClickListener {

            if (!expandableLayout) {
                contentContainer.visibility = View.VISIBLE
                expandImg.setImageResource(R.drawable.ic_less)
                expandableLayout = true
            } else {
                contentContainer.visibility = View.GONE
                expandImg.setImageResource(R.drawable.ic_more)
                expandableLayout = false
            }

        })


        return view
    }

    private fun saveBookmark(position: Int, soraName: String, soraNum: Int){

        val bookMarkEntity = BookMarkEntity(0, position+1, soraName, position, soraNum)

        bookmarkViewModel.insert(bookMarkEntity)
        Toast.makeText(context, getText(R.string.ayahAddedToBookmark), Toast.LENGTH_SHORT).show()
    }

    private fun addToFavorite(position: Int, list: List<SoraWithTafsir>?) {
        val favoriteEntity = FavoriteEntity(
            0,
            position+1,
            _sowraNum,
            list!![position].soraName,
            list[position].ayah,
            list[position].tafsir,
            true
        )
        favoriteViewModel.insert(favoriteEntity)
        Toast.makeText(context, getText(R.string.ayahAddedToFavorite), Toast.LENGTH_SHORT).show()
    }


    private fun tafsirPopUpMenu(
        list: List<SoraWithTafsir>,
        soraNum: Int,
        position: Int,
        view: View,
        context: Context?
    ) {

        mainViewModel.setPosition(position)

        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.tafasir_menu)
        popup.setOnMenuItemClickListener { item ->


            when (item.itemId) {
                R.id.moyasar -> {
                    getTafsir(1, soraNum, position, getString(R.string.moyasar))
                }
                R.id.jalalain -> {

                    getTafsir(2, soraNum, position, getString(R.string.jalalain))

                }
                R.id.saidi -> {
                    getTafsir(3, soraNum, position, getString(R.string.saidi))
                }
                R.id.ibenKatir -> {
                    getTafsir(4, soraNum, position, getString(R.string.ibenKatir))
                }
                R.id.tentawi -> {
                    getTafsir(5, soraNum, position, getString(R.string.tentawi))
                }
                R.id.baghawi -> {

                    getTafsir(6, soraNum, position, getString(R.string.baghawi))
                }
                R.id.qurtobi -> {
                    getTafsir(7, soraNum, position, getString(R.string.qurtobi))
                }
                R.id.tabari -> {
                    getTafsir(8, soraNum, position, getString(R.string.tabari))
                }

            }

            false
        }

        popup.show()
    }


    fun getTafsir(id: Int, soraNum: Int, position: Int, message: String) {

        mainViewModel.tafsir(soraNum, position, id)

        mainViewModel.getTafsir(soraNum, position, id) { list ->

            mainViewModel.soraWithTafsir.value!![position - 1].mofasir_name =
                list.mofasir_name
            mainViewModel.soraWithTafsir.value!![position - 1].tafsir = list.tafsir



            recycleCopy!!.adapter!!.notifyDataSetChanged()

            val goToPosition = mainViewModel.getPosition()
            if (goToPosition != 0) {
                println("--------- Go To Position Number: $goToPosition")
                recycleCopy!!.scrollToPosition(goToPosition - 1)
            }


            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

            passData(list.tafsir, list.mofasir_name)



        }


    }

    fun saveState() {
        if (_state != null) {
            mainViewModel.setAyahRecycleState(MainViewModel.SowraState(_sowraNum, _state))
        }
    }

    fun passData(newTafsir:String, newMofasir:String){
        tfsir = newTafsir
        mofsirName = newMofasir
        isDefault = false
    }

    override fun onPause() {
        saveState()
        super.onPause()
    }
}