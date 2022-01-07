package com.network.tafasir.DATA.Database.directSqlite

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class DatabaseAccess private constructor(context: Context) {
    private val openHelper: SQLiteAssetHelper
    private var db: SQLiteDatabase? = null
    var cursor: Cursor? = null

    init {
        openHelper = SqliteOpenHelper(context)
    }

    fun open() {
        db = openHelper.writableDatabase
    }

    fun close() {
        if (db != null) {
            db!!.close()
        }
    }

    fun getStudentInfo(num: Int): String {
        var result = ""
        cursor = db!!.rawQuery("SELECT about_sora FROM sowar WHERE sora_number = $num", null)
        if (cursor != null) {
            if (cursor!!.moveToFirst()) {
                //Extracting Data From Rows
                result = cursor!!.getString(cursor!!.getColumnIndex("about_sora"))
            }
            cursor!!.close()
            return result
        }
        return "No Result!"
    }

    companion object {
        private var instance: DatabaseAccess? = null
        fun getInstance(context: Context): DatabaseAccess? {
            if (instance == null) {
                instance = DatabaseAccess(context)
            }
            return instance
        }
    }


}