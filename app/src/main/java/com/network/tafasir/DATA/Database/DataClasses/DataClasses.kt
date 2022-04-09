package com.network.tafasir.DATA.Database.DataClasses

import java.text.FieldPosition

data class SoraWithTafsir(
    val soraName: String,
    val soraType: String,
    val ayat_number: Int,
    val order_number: Int,
    val prostration: Boolean,
    val ayah: String,
    var mofasir_name: String,
    var tafsir: String
)

data class Tafsir(
    val mofasir_name: String,
    val tafsir: String
)

data class TafsirWithAyah(
    val ayah: String,
    val mofasir_name: String,
    val tafsir: String
)

data class Sowara(
    val sora_number: Int,
    val sora_name: String,
    val ayat_number: Int,
    val sora_type: String
)

data class Ayah(
    val ayah: String,
    val ayah_number:Int,
    val sora_number:Int
)

data class AboutSora(
    val about: String
)

data class FavouriteAyah(
    val ayah_number: Int,
    val soraName:String,
    val ayah:String,
    val tafsir:String
)

data class BookMarkAyah(
    val ayahNumber: Int,
    val soraName: String,
    val position: Int,
    val soraNumber: Int
)
