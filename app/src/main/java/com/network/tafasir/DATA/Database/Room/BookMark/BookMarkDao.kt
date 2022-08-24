package com.network.tafasir.DATA.Database.Room.BookMark

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.network.tafasir.DATA.Database.DataClasses.BookMarkAyah

@Dao
interface BookMarkDao {

    @Query("SELECT * FROM book_mark")
    suspend fun getAll(): MutableList<BookMarkEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bookMarkEntity: BookMarkEntity)

    @Delete
    suspend fun delete(bookMarkEntity: BookMarkEntity)
}