package com.mabahmani.data.util


fun Long?.toHumanReadableTime(): String{
    return if (this != null){
        try {
            val secs: Long = this / 1000
            return String.format("%d:%02d",secs % 3600 / 60, secs % 60)
        }catch (ex: Exception){
            "0"
        }

    } else{
        "0"
    }
}

fun Int?.toHumanReadableTime(): String{
    return if (this != null){
        try {
            val secs =  this
            String.format("%d:%02d",secs % 3600 / 60, secs % 60)
        }catch (ex: Exception){
            "0"
        }

    } else{
        "0"
    }
}