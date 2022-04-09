package com.network.tafasir.DATA.Database.Room.Favorite

import android.app.Application
import com.network.tafasir.DATA.Database.DataClasses.FavouriteAyah
import com.network.tafasir.DATA.Database.Room.BookMark.BookMarkDao
import com.network.tafasir.DATA.Database.Room.RoomDatabaseAccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteRepo(val application: Application) {

    private val favoriteDoa = RoomDatabaseAccess.getDatabase(application).favoriteDoa()

    suspend fun getAllFavorite():List<FavouriteAyah>{
        var list: List<FavouriteAyah>
        //
        val job = CoroutineScope(Dispatchers.IO).async {
            list = favoriteDoa.getAll()
            list
        }
        //
        return job.await()
    }

    suspend fun insert(favoriteEntity: FavoriteEntity){
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDoa.insert(favoriteEntity)
        }
    }

    suspend fun delete(favoriteEntity: FavoriteEntity){
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDoa.delete(favoriteEntity)
        }
    }
}