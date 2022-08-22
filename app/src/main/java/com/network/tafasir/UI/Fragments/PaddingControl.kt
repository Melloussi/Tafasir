package com.network.tafasir.UI.Fragments

import android.view.View

class PaddingControl (val view:View){

    private var isTherePadding = false

    fun removePadding() {

        if(isTherePadding){
            view?.setPadding(0, 0, 0, 0)
            isTherePadding = false
        }
    }
    fun addPadding(){

        if(!isTherePadding){

            //Convert From dp to Pixels size
            val myDpSize = 70
            val scale = view.resources.displayMetrics.density
            val dpAsPixels = (myDpSize * scale + 0.5f)

            view?.setPadding(0, 0, 0, dpAsPixels.toInt())

            isTherePadding = true
        }
    }
}