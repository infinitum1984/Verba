package com.emptydev.vebra

import android.util.Log

fun mapToString(map: Map<String,String>):String{
    var outString=""
    for (it in map){
        outString+="${it.key},${it.value}."
    }
    return outString
}
fun stringToMap(s:String): Map<String,String>{
    var outMap= HashMap<String,String>()

    val pairs= s.split(".")
    Log.d("D_Util", "stringToMap: ${pairs.size}")
    for (it in pairs){
        if (it.isNotEmpty()) {
            val pair = it.split(",")
            outMap.put(pair[0], pair[1])
        }
    }
    return outMap
}