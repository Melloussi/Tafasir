package com.network.tafasir.UI.ViewModel

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.network.tafasir.DATA.Database.DataClasses.*
import com.network.tafasir.DATA.Database.Repository.Repo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application), ViewModelFunctions {
    private var repo:Repo = Repo(application)
    //
    var soraWithTafsir = MutableLiveData<List<SoraWithTafsir>>()
    var tafsir = MutableLiveData<Tafsir>()
    var tfsirWithAyah = MutableLiveData<TafsirWithAyah>()
    var allSowar = MutableLiveData<List<Sowara>>()
    var soraSearch = MutableLiveData<List<Sowara>>()
    var ayahSearch = MutableLiveData<List<Ayah>>()
    var aboutSowra = MutableLiveData<AboutSora>()

    private var sowarIndexRecylerViewState:Parcelable? = null
    private var sowraState:SowraState? = null
    private var position:Int = 0


    data class SowraState(var soraNum:Int, var viewState:Parcelable?)



    override fun soraWithTafsir(soraNum: Int, tafsirNum: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            //
            soraWithTafsir.value = repo.soraWithTafsir(soraNum, tafsirNum)
        }
    }

    override fun tafsir(soraNum: Int, ayahNum: Int, tafsirNum: Int, ) {
        CoroutineScope(Dispatchers.Main).launch {
            //
            val tt = repo.tafsir(soraNum, ayahNum, tafsirNum)
        }

    }

    fun getTafsir(soraNum: Int, ayahNum: Int, tafsirNum: Int, result: (Tafsir) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            //
            val tt = repo.tafsir(soraNum, ayahNum, tafsirNum)
            result(tt)
        }

    }

    override fun tfsirWithAyah(soraNum: Int, ayahNum: Int, tafsirNum: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            //
            tfsirWithAyah.value = repo.tfsirWithAyah(soraNum, ayahNum, tafsirNum)
        }
    }

    override fun allSowar() {
        CoroutineScope(Dispatchers.Main).launch {
            //
            allSowar.value = repo.allSowar()
        }
    }

    override fun soraSearch(keyword: String) {
        CoroutineScope(Dispatchers.Main).launch {
            //
            soraSearch.value = repo.soraSearch(keyword)
        }
    }

    override fun ayahSearch(keyword: String) {
        CoroutineScope(Dispatchers.Main).launch {
            //
            ayahSearch.value = repo.ayahSearch(keyword)
        }
    }

    override fun aboutSowra(soraNum: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            //
            aboutSowra.value = repo.aboutSowra(soraNum)
        }
    }

    fun getSowarIndexRecycleState() : Parcelable?{
        return sowarIndexRecylerViewState
    }

    fun setSowarIndexRecycleState(state:Parcelable){
        sowarIndexRecylerViewState = state
    }

    fun getAyahRecycleState() : SowraState?{
        return sowraState
    }

    fun setAyahRecycleState(state:SowraState){
        sowraState = state
    }

    fun getPosition():Int{
        return position
    }

    fun setPosition(_position:Int){
        position = _position
    }




}