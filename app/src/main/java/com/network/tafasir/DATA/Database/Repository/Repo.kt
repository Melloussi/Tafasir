package com.network.tafasir.DATA.Database.Repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.network.tafasir.DATA.Database.DataClasses.*
import com.network.tafasir.DATA.Database.directSqlite.ExecuteCommand.SqliteExecuteCommand
import com.network.tafasir.DATA.Testing.CountTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.ArrayList

class Repo(application: Application) : RepoInt {

    private val sec: SqliteExecuteCommand = SqliteExecuteCommand(application)
    private var isOpen = false

    override suspend fun soraWithTafsir(
        soraNum: Int,
        tafsirNum: Int
    ): List<SoraWithTafsir> {
        val list = ArrayList<SoraWithTafsir>()

        val job = CoroutineScope(Dispatchers.IO).async {
            sec.open()
            //
            val cursor = sec.soraWithTafsir(soraNum, tafsirNum)


            //
            try {
                if (cursor.moveToFirst()) {


                    while (!cursor.isAfterLast) {

                            list.add(
                                SoraWithTafsir(
                                    cursor.getString(cursor.getColumnIndex("sora_name")),
                                    cursor.getString(cursor.getColumnIndex("type")),
                                    cursor.getInt(cursor.getColumnIndex("ayat_number")),
                                    cursor.getInt(cursor.getColumnIndex("order_number")),
                                    cursor.getInt(cursor.getColumnIndex("prostration")) == 1 ,
                                    cursor.getString(cursor.getColumnIndex("ayah")),
                                    cursor.getString(cursor.getColumnIndex("mofasir_name")),
                                    cursor.getString(cursor.getColumnIndex("tafsir"))
                                )
                            )
                            //
                            cursor.moveToNext()
                        }
                }
            } finally {
                cursor.close()
                sec.close()
            }
            list
        }

        return job.await()
    }

    override suspend fun tafsir(soraNum: Int, ayahNum: Int, tafsirNum: Int): Tafsir {

        var result: Tafsir? = null
        //
        val job = CoroutineScope(Dispatchers.IO).async {
            sec.open()
            //
            val cursor = sec.tafsir(soraNum, ayahNum, tafsirNum)
            //
            try {
                if (cursor.moveToFirst()) {
                    result = Tafsir(
                        cursor.getString(cursor.getColumnIndex("mofasir_name")),
                        cursor.getString(cursor.getColumnIndex("tafsir"))
                    )
                }
            } finally {
                sec.close()
            }
            result
        }
        //
        return job.await()!!
    }

    override suspend fun tfsirWithAyah(
        soraNum: Int,
        ayahNum: Int,
        tafsirNum: Int
    ): TafsirWithAyah {
        var result:TafsirWithAyah? = null
        //
        val job = CoroutineScope(Dispatchers.IO).async {

            sec.open()
            //
            val cursor = sec.tfsirWithAyah(soraNum, ayahNum, tafsirNum)
            //
            try {
                if (cursor.moveToFirst()) {
                    result = TafsirWithAyah(
                        cursor.getString(cursor.getColumnIndex("ayah")),
                        cursor.getString(cursor.getColumnIndex("mofasir_name")),
                        cursor.getString(cursor.getColumnIndex("tafsir"))
                    )
                }
            } finally {
                sec.close()
            }
            result
        }
        //

        return job.await()!!
    }

    override suspend fun allSowar(): List<Sowara> {
        val list = ArrayList<Sowara>()
        var num = 0

        val job = CoroutineScope(Dispatchers.IO).async {
            sec.open()
            //
            val cursor = sec.allSowar()
            //
            try {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast) {
                        list.add(
                            Sowara(
                                cursor.getInt(cursor.getColumnIndex("sora_number")),
                                cursor.getString(cursor.getColumnIndex("sora_name")),
                                cursor.getInt(cursor.getColumnIndex("ayat_number")),
                                cursor.getString(cursor.getColumnIndex("type"))
                            )
                        )
                        cursor.moveToNext()
                    }
                }
            } finally {
                cursor.close()
                sec.close()
            }
            list
        }

        return job.await()
    }

    override suspend fun soraSearch(keyword: String): List<Sowara> {
        val list = ArrayList<Sowara>()

        val job = CoroutineScope(Dispatchers.IO).async {
            sec.open()
            //
            val cursor = sec.soraSearch(keyword)
            //
            try {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast) {
                        list.add(
                            Sowara(
                                cursor.getInt(cursor.getColumnIndex("sora_number")),
                                cursor.getString(cursor.getColumnIndex("sora_name")),
                                cursor.getInt(cursor.getColumnIndex("ayat_number")),
                                cursor.getString(cursor.getColumnIndex("type"))
                            )
                        )
                        cursor.moveToNext()
                    }
                }
            } finally {
                sec.close()
            }
            list
        }

        return job.await()
    }

    override suspend fun ayahSearch(keyword: String): List<Ayah> {
        val list = ArrayList<Ayah>()

        val job = CoroutineScope(Dispatchers.IO).async {
            sec.open()
            //
            val cursor = sec.ayahSearch(keyword)
            //
            try {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast) {
                        list.add(
                            Ayah(
                                cursor.getString(cursor.getColumnIndex("ayah")),
                                cursor.getInt(cursor.getColumnIndex("ayah_number")),
                                cursor.getInt(cursor.getColumnIndex("sora_number")),
                            )
                        )
                        cursor.moveToNext()
                    }
                }
            } finally {
                sec.close()
            }
            list
        }

        return job.await()
    }

    override suspend fun aboutSowra(soraNum: Int): AboutSora {
        var result:AboutSora? = null
        //
        val job = CoroutineScope(Dispatchers.IO).async {
            sec.open()
            //
            val cursor = sec.aboutSowra(soraNum)
            //
            try {
                if (cursor.moveToFirst()) {
                    result =
                        AboutSora(cursor.getString(cursor.getColumnIndex("about_sora")))
                }
            } finally {
                cursor.close()
                sec.close()
            }
            result
        }
        //
        return job.await()!!
    }
}