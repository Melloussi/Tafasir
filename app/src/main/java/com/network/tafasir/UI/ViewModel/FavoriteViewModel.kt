package com.network.tafasir.UI.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.network.tafasir.DATA.Database.DataClasses.FavouriteAyah
import com.network.tafasir.DATA.Database.Room.Favorite.FavoriteEntity
import com.network.tafasir.DATA.Database.Room.Favorite.FavoriteRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val getAll = MutableLiveData<MutableList<FavoriteEntity>>()
    private val repo = FavoriteRepo(application)

    val allFavorite = getAll

    init {
        getAll()
    }

    fun getAll(){
        CoroutineScope(Dispatchers.Main).launch {
            getAll.value = repo.getAllFavorite()
        }
    }

    fun insert(favoriteEntity: FavoriteEntity){
        CoroutineScope(Dispatchers.IO).launch {
            repo.insert(favoriteEntity)
        }
    }

    fun delete(favoriteEntity: FavoriteEntity){
        CoroutineScope(Dispatchers.IO).launch {
            repo.delete(favoriteEntity)
        }
    }
}