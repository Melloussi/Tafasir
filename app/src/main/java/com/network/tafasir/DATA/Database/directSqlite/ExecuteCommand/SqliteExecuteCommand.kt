package com.network.tafasir.DATA.Database.directSqlite.ExecuteCommand

import android.content.Context
import android.database.Cursor
import com.network.tafasir.DATA.Database.directSqlite.DatabaseAccess

class SqliteExecuteCommand(context: Context) : SqliteExecuteCommandInt {
    private var dataAccess: DatabaseAccess = DatabaseAccess.getInstance(context)!!


    init {
        dataAccess.open()
        printMessage(0)
    }

    override suspend fun soraWithTafsir(soraNum: Int, tafsirNum: Int): Cursor {
        val sql =
            "SELECT sowar.sora_name, sowar.type, sowar.ayat_number, sowar.order_number, sowar.prostration, ayats.ayah," +
                    " tafasir.mofasir_name, tafasir.tafsir" +
                    " FROM sowar" +
                    " INNER JOIN ayats ON sowar.sora_number = ayats.sora_number" +
                    " INNER JOIN tafasir ON ayats.ayah_number = tafasir.aya_number" +
                    " WHERE sowar.sora_number = $soraNum AND tafasir.sora_number = $soraNum AND tafasir.tafsir_number = $tafsirNum;"
        //
        return dataAccess.db!!.rawQuery(sql, null)
    }

    override suspend fun tafsir(soraNum: Int, ayahNum: Int, tafsirNum: Int): Cursor {
        val sql =
            "SELECT mofasir_name, tafsir FROM tafasir WHERE tafasir.sora_number = $soraNum AND tafasir.aya_number = $ayahNum AND tafasir.tafsir_number = $tafsirNum;"
        //
        return dataAccess.db!!.rawQuery(sql, null)
    }

    override suspend fun tfsirWithAyah(soraNum: Int, ayahNum: Int, tafsirNum: Int): Cursor {
        val sql = "SELECT ayats.ayah, tafasir.mofasir_name, tafasir.tafsir" +
                " FROM tafasir" +
                " JOIN sowar ON ayats.sora_number = sowar.sora_number" +
                " JOIN ayats ON tafasir.aya_number = ayats.ayah_number AND tafasir.sora_number = sowar.sora_number" +
                " WHERE tafasir.sora_number = $soraNum AND tafasir.aya_number = $ayahNum AND tafsir_number = $tafsirNum;"
        //
        return dataAccess.db!!.rawQuery(sql, null)
    }

    override suspend fun allSowar(): Cursor {
        val sql = "SELECT sora_number, sora_name, ayat_number, type FROM sowar"
        //
        return dataAccess.db!!.rawQuery(sql, null)
    }

    override suspend fun soraSearch(keyword: String): Cursor {
        val sql =
            "SELECT sora_number, sora_name, ayat_number, type FROM sowar WHERE sora_name like '%$keyword%'"
        //
        return dataAccess.db!!.rawQuery(sql, null)
    }

    override suspend fun ayahSearch(keyword: String): Cursor {
        val sql =
            "SELECT ayah, ayah_number, ayats.sora_number FROM ayats WHERE ayah_normal like '%$keyword%'"
        //
        return dataAccess.db!!.rawQuery(sql, null)
    }

    override suspend fun aboutSowra(soraNum: Int): Cursor {
        val sql = "SELECT about_sora FROM sowar WHERE sora_number = $soraNum;"
        //
        return dataAccess.db!!.rawQuery(sql, null)
    }

    fun close() {
        //dataAccess.close()
        printMessage(1)
    }

    fun open() {

        dataAccess.open()
        printMessage(0)

    }
    fun printMessage(num:Int){
        if(num == 0){
            println("====== Db Open =====")
        }else{
            println("====== Db Closed =====")
        }
    }


}