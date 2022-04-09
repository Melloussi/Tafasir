package com.network.tafasir.DATA.Database.Room.BookMark

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.network.tafasir.DATA.Database.DataClasses.BookMarkAyah

@Entity(tableName = "book_mark")
data class BookMarkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val ayahNumber: Int,
    val soraName: String,
    val position: Int,
    val soraNumber: Int

)