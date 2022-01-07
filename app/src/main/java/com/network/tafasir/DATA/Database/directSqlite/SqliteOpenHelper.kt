package com.network.tafasir.DATA.Database.directSqlite

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class SqliteOpenHelper(context: Context?) : SQLiteAssetHelper(context, DTATABASE_NAME, null, DTATABASE_VERSION) {
    companion object {
        private const val DTATABASE_NAME = "quran.db"
        private const val DTATABASE_VERSION = 1
    }
}
