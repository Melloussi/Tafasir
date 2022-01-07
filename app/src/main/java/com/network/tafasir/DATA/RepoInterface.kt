package com.network.tafasir.DATA

interface RepoInterface {

    //get quran with information
    suspend fun quranWithTafsir(soraNum:Int, tafsirNum:Int)

    //get tafsir
    suspend fun tafsir(soraNum:Int, ayahNum:Int, tafsirNum:Int)

    //get tafsir With Ayah
    suspend fun tfsirWithAyah(soraNum:Int, ayahNum:Int, tafsirNum:Int)

    //get sowar
    suspend fun allSowar()

    //sowra search
    suspend fun soraSearch(keyword:String)

    //ayah search
    suspend fun ayahSearch(keyword:String)

}