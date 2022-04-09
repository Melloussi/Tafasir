package com.network.tafasir.UI.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.network.tafasir.DATA.Database.DataClasses.BookMarkAyah
import com.network.tafasir.DATA.Database.Room.BookMark.BookMarkEntity
import com.network.tafasir.DATA.Database.Room.BookMark.BookMarkRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookMarkViewModel(application: Application) : AndroidViewModel(application) {
    private val getAll = MutableLiveData<List<BookMarkAyah>>()
    private val repo = BookMarkRepo(application)
    val result = getAll

    init {
        getAll()
    }

    fun getAll(){
        CoroutineScope(Dispatchers.Main).launch {
            getAll.value = repo.getAllBookMarks()
        }
    }

    fun insert(bookMarkEntity: BookMarkEntity){
        CoroutineScope(Dispatchers.IO).launch {
            repo.insert(bookMarkEntity)
        }
    }

    fun delete(bookMarkEntity: BookMarkEntity){
        CoroutineScope(Dispatchers.IO).launch {
            repo.delete(bookMarkEntity)
        }
    }
}