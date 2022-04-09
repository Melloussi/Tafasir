package com.network.tafasir.UI.ViewModel

import com.network.tafasir.DATA.Database.DataClasses.*

interface ViewModelFunctions {
    fun soraWithTafsir(soraNum: Int, tafsirNum: Int)

    fun tafsir(soraNum: Int, ayahNum: Int, tafsirNum: Int)

    fun tfsirWithAyah(soraNum: Int, ayahNum: Int, tafsirNum: Int)

    fun allSowar()

    fun soraSearch(keyword: String)

    fun ayahSearch(keyword: String)

    fun aboutSowra(soraNum: Int)
}