package com.network.tafasir.DATA.Database.Repository

import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import com.network.tafasir.DATA.Database.DataClasses.*

interface RepoInt {

    suspend fun soraWithTafsir(soraNum: Int, tafsirNum: Int): List<SoraWithTafsir>

    suspend fun tafsir(soraNum: Int, ayahNum: Int, tafsirNum: Int): Tafsir

    suspend fun tfsirWithAyah(soraNum: Int, ayahNum: Int, tafsirNum: Int): TafsirWithAyah

    suspend fun allSowar(): List<Sowara>

    suspend fun soraSearch(keyword: String): List<Sowara>

    suspend fun ayahSearch(keyword: String): List<Ayah>

    suspend fun aboutSowra(soraNum: Int): AboutSora
}