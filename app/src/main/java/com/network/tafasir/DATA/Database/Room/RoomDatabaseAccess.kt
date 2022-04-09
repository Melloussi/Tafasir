package com.network.tafasir.DATA.Database.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.network.tafasir.DATA.Database.Room.BookMark.BookMarkDao
import com.network.tafasir.DATA.Database.Room.BookMark.BookMarkEntity
import com.network.tafasir.DATA.Database.Room.Favorite.FavoriteDoa
import com.network.tafasir.DATA.Database.Room.Favorite.FavoriteEntity

@Database(entities = [BookMarkEntity::class, FavoriteEntity::class], version = 1, exportSchema = false)
abstract class RoomDatabaseAccess: RoomDatabase() {
    abstract fun bookMarkDoa(): BookMarkDao
    abstract fun favoriteDoa(): FavoriteDoa

    companion object{
        @Volatile
        private var INSTANCE: RoomDatabaseAccess? = null

        fun getDatabase(context: Context):RoomDatabaseAccess{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabaseAccess::class.java,
                    "database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}