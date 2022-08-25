package com.network.tafasir.DATA.Database.Room.Favorite

import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.network.tafasir.DATA.Database.DataClasses.FavouriteAyah

@Dao
interface FavoriteDoa {

    @Query("SELECT * FROM favorite_ayah")
    suspend fun getAll():MutableList<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteEntity: FavoriteEntity)

    @Delete
    suspend fun delete(favoriteEntity: FavoriteEntity)
}