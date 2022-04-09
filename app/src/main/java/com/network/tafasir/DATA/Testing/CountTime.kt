package com.network.tafasir.DATA.Testing

import java.lang.System.currentTimeMillis

class CountTime {
    var time:Long = 0

    fun start(){
        time = currentTimeMillis()
    }
    fun stop(){
        time = currentTimeMillis()-time
        val timeInSec = time/1000
        println("===== Time in Second: [$timeInSec] Time in MilliSec: [$time] =====")
    }
}