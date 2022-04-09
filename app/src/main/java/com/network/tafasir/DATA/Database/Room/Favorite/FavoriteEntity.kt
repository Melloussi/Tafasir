package com.network.tafasir.DATA.Database.Room.Favorite

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.network.tafasir.DATA.Database.DataClasses.FavouriteAyah

@Entity(tableName = "favorite_ayah")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val ayah_number: Int,
    val soraName:String,
    val ayah:String,
    val tafsir:String,
    val isFavorite:Boolean

)