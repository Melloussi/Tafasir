package com.network.tafasir.DATA.Database.Room.BookMark

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.network.tafasir.DATA.Database.DataClasses.BookMarkAyah
import com.network.tafasir.DATA.Database.Room.RoomDatabaseAccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BookMarkRepo(application: Application) {

    private val bookMarkDao:BookMarkDao = RoomDatabaseAccess.getDatabase(application).bookMarkDoa()

    suspend fun getAllBookMarks(): MutableList<BookMarkEntity> {
        var list:MutableList<BookMarkEntity>
        //
        val job = CoroutineScope(Dispatchers.IO).async {
            list = bookMarkDao.getAll()
            list
        }

        return job.await()
    }

    suspend fun insert(bookMarkEntity: BookMarkEntity){
        CoroutineScope(Dispatchers.IO).launch{
            bookMarkDao.insert(bookMarkEntity)
        }
    }

    suspend fun delete(bookMarkEntity: BookMarkEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            bookMarkDao.delete(bookMarkEntity)

        }
    }
}