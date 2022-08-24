package com.network.tafasir.DATA.Database.Room.BookMark

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.network.tafasir.DATA.Database.DataClasses.BookMarkAyah

//@Entity(tableName = "book_mark")
@Entity(tableName = "book_mark", indices = [Index(value = ["ayahNumber","soraNumber"], unique = true)])
data class BookMarkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val ayahNumber: Int,
    val soraName: String,
    val position: Int,
    val soraNumber: Int

)


