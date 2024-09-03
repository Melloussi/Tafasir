
package com.network.tafasir.UI.Activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.network.tafasir.R
import com.network.tafasir.UI.Communication.Communicat
import com.network.tafasir.UI.Fragments.*
import com.network.tafasir.UI.ViewModel.MainViewModel
import java.lang.reflect.Field


class MainActivity : AppCompatActivity(), Communicat, SearchView.OnQueryTextListener {

    private lateinit var mainViewModel: MainViewModel
    var activeFragment: ActiveFragment? = null
    private lateinit var searchViewCopy: SearchView
    private var flag = false
    private var soraNum: Int = 1
    private var search:MenuItem? = null
    private var bottomNav:BottomNavigationView? = null
    private var pressedTime:Long = 0
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    sealed class ActiveFragment {
        object SwarIndex : ActiveFragment()
        object ReadSowra : ActiveFragment()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var num = 1

        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = Firebase.analytics


        firebaseAnalytics = FirebaseAnalytics.getInstance(this);


        //supportActionBar!!.hide()
        val drawable = getDrawable(R.drawable.tool_bar_shape)
        supportActionBar!!.setBackgroundDrawable(drawable)
        supportActionBar!!.setDisplayShowTitleEnabled(false)




        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        addFragment(SowarIndexF(), "MY_FRAGMENT")

        bottomNav?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.reading -> {addFragment(SowarIndexF(),"other")}
                R.id.favorite -> {addFragment(FavoriteF(),"other")}
                R.id.bookMark -> {addFragment(BookMarkF(),"other")}
            }
            true
        }

    }

    private fun colors(colorId: Int): Int {
        return ContextCompat.getColor(
            this, colorId
        )
    }


    fun addFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, tag)
            .commitAllowingStateLoss()

        when (fragment) {
            is SowarIndexF -> {
                activeFragment = ActiveFragment.SwarIndex
                search?.isVisible = true
                bottomNav?.isVisible = true
            }
            is ReadSowraF -> {
                activeFragment = ActiveFragment.ReadSowra
                search?.isVisible = true
                bottomNav?.isVisible = false

            }
            is FavoriteF -> {
                search?.isVisible = false
                bottomNav?.isVisible = true
            }
        }
        if (flag) {
            hint(searchViewCopy)
        }


    }

    override fun TransferData(_soraNum: Int, tafsirNum: Int, ayahNum:Int) {

        val bundle = Bundle()
        bundle.putInt("soraNum", _soraNum)
        bundle.putInt("tafsirNum", tafsirNum)
        bundle.putInt("ayahNum", ayahNum)

        val readSowra = ReadSowraF()
        readSowra.arguments = bundle

        addFragment(readSowra, "other")

        soraNum = _soraNum

    }

    override fun onBackPressed() {
        //super.onBackPressed()

        val myFragment = supportFragmentManager.findFragmentByTag("MY_FRAGMENT")
        val test = myFragment?.isVisible

        if (test == null) {
            addFragment(SowarIndexF(), "MY_FRAGMENT")
        } else {
            println("Press Twice To Quite")
        }


    }

    @SuppressLint("RtlHardcoded")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        search = menu!!.findItem(R.id.searchMenu)

        val searchView = search!!.actionView as SearchView
        searchViewCopy = searchView

        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)


        val searchTextView: AutoCompleteTextView = searchView.findViewById(R.id.search_src_text)

        searchTextView.textAlignment = AutoCompleteTextView.TEXT_ALIGNMENT_VIEW_END
        //set background shape
        searchTextView.setBackgroundResource(R.drawable.rounded_corner_white)
        //set Color Hint
        searchTextView.setHintTextColor(colors(R.color.Green))
        //set Text Color
        searchTextView.setTextColor(colors(R.color.DarkGreen))


//        val mCursorDrawableRes: Field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
//        mCursorDrawableRes.isAccessible = true
//        mCursorDrawableRes.set(searchTextView, R.drawable.cursor)







        hint(searchView)
        flag = true

        return true
    }

    private fun hint(searchView: SearchView) {
        when (activeFragment) {
            ActiveFragment.SwarIndex -> {
                searchView.queryHint = getString(R.string.searchForSora)
                println(R.string.searchForSora)
            }
            ActiveFragment.ReadSowra -> {
                searchView.queryHint = getString(R.string.searchForAyah)
                println(R.string.searchForAyah)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {


        when (activeFragment) {
            ActiveFragment.SwarIndex -> {
                sowraIndexSearch(newText)
            }
            ActiveFragment.ReadSowra -> {
                ayahSearch(newText)
            }
        }
        return true
    }

    private fun ayahSearch(newText: String?) {
        var flag = true

        if (newText != null && newText.length >= 3) {

            if (flag) {
                //
                addFragment(AyahtSuggestionF(), "other")
                flag = false
            }
            mainViewModel.ayahSearch(newText)
        } else if (newText.isNullOrEmpty()) {
            TransferData(soraNum, 1, 0)
            flag = true
        }
    }

    private fun sowraIndexSearch(newText: String?) {
        if (newText != null && newText.length >= 3) {
            mainViewModel.soraSearch(newText)
        } else if (newText.isNullOrEmpty()) {
            mainViewModel.allSowar()
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        val toastMessage = Toast.makeText(this, R.string.exitMessage, Toast.LENGTH_SHORT)

        if(pressedTime + 2000 > System.currentTimeMillis()){
            toastMessage.cancel()
            this.finishAffinity()
        }else{
            toastMessage.show()
        }

        pressedTime = System.currentTimeMillis()

        return super.onKeyDown(keyCode, event)
    }




}