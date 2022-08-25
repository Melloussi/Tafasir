package com.network.tafasir.UI.Controlers

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.network.tafasir.DATA.Database.DataClasses.SoraWithTafsir
import com.network.tafasir.R

class ShareContent {

    data class SharedData(val soraName: String?
    , val ayah: String?
    , val soraNum:Int
    , val AyahNum:Int
    , val tafsir: String?
    , val mofasirName: String?)

    fun shareContent(
        data: SharedData,
        view: View,
        context: Context
    ) {

        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.share_floating_menu)
        popup.setOnMenuItemClickListener { item ->
            val id = item.itemId
            when (id) {
                R.id.copyAyah -> {
                    val text = formatText(
                        data.soraName,
                        data.AyahNum,
                        data.ayah,
                        null,
                        null
                    )

                    copyToClipBoard(text, context)
                    Toast.makeText(context, context.getString(R.string.copyAyahDone), Toast.LENGTH_SHORT)
                        .show()
                }
                R.id.copyTafsir -> {
                    val text = formatText(
                        null,
                        null,
                        null,
                        data.mofasirName,
                        data.tafsir
                    )

                    copyToClipBoard(text, context)
                    Toast.makeText(context, context.getString(R.string.copyTafsirDone), Toast.LENGTH_SHORT)
                        .show()
                }
                R.id.copyAyahWithTafsir -> {
                    val text = formatText(
                        data.soraName,
                        data.AyahNum,
                        data.ayah,
                        data.mofasirName,
                        data.tafsir
                    )

                    copyToClipBoard(text, context)
                    Toast.makeText(
                        context,
                        context.getString(R.string.copyAyahWithTafsirDone),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.share -> {
                    val text = formatText(
                        data.soraName,
                        data.AyahNum,
                        data.ayah,
                        data.mofasirName,
                        data.tafsir
                    )
                    val shareText = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, text)
                        type = "text/plain"
                    }
                    context.startActivity(shareText)
                }
            }

            false
        }
        popup.show()
    }

    fun formatText(
        soraName: String?,
        ayahNumber: Number?,
        ayah: String?,
        mofasirName: String?,
        tafsir: String?
    ): String {
        var ayahText = ""
        var tafsirText = ""

        if (soraName != null) {
            ayahText = " ---------- الآية $ayahNumber  من سورة $soraName ----------" +
                    "\n\n $ayah"
        }
        if (mofasirName != null) {
            tafsirText = "\n\n ---------- $mofasirName ---------- \n\n $tafsir "
        }

        return "$ayahText $tafsirText"
    }

    fun copyToClipBoard(text: String, context: Context) {
        val clipboard = ContextCompat.getSystemService(
            context,
            ClipboardManager::class.java
        ) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
    }
}
