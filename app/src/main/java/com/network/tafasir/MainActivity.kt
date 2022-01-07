package com.network.tafasir

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.network.tafasir.DATA.Database.directSqlite.DatabaseAccess

class MainActivity : AppCompatActivity() {
    lateinit var tv: TextView
    lateinit var btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var num = 1

        tv  = findViewById(R.id.tv)
        btn  = findViewById(R.id.btn)
//        val text = "<p><big><b> My Introduction </b></big></p>  <br/> <b> profusions: </b> normal Text Here Will Be Good, I hope This Look Amazing "
//
//        tv.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)

        //dispaly()

        val text = "this is a text and <br/> another test again"
//
        tv.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
        btn.setOnClickListener(View.OnClickListener {
            dispaly(num)

            if(num < 23){
                num += 1
            }
        })

    }

    private fun dispaly(num:Int) {
        var result = ""
        val dataAccess: DatabaseAccess = DatabaseAccess.getInstance(this)!!
        dataAccess.open()
        result = dataAccess.getStudentInfo(num)
        dataAccess.close()

        println(result)


        tv.text = HtmlCompat.fromHtml(result, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}