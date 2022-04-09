package com.network.tafasir.DATA.Database.directSqlite.ExecuteCommand

import android.database.Cursor

interface SqliteExecuteCommandInt {
    //get quran with information
    suspend fun soraWithTafsir(soraNum:Int, tafsirNum:Int): Cursor

    //get tafsir
    suspend fun tafsir(soraNum:Int, ayahNum:Int, tafsirNum:Int):Cursor

    //get tafsir With Ayah
    suspend fun tfsirWithAyah(soraNum:Int, ayahNum:Int, tafsirNum:Int):Cursor

    //get sowar
    suspend fun allSowar():Cursor

    //sowra search
    suspend fun soraSearch(keyword:String):Cursor

    //ayah search
    suspend fun ayahSearch(keyword:String):Cursor

    suspend fun aboutSowra(soraNum: Int):Cursor
}