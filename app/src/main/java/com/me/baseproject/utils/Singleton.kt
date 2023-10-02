package com.me.baseproject.utils

import com.livevalue.customer.apiServices.ApiClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

// https://medium.com/kayvan-kaseb/singleton-pattern-in-kotlin-7a6b422acd81
object Singleton {
    init {
        println("Singleton class invoked.")
    }

    val apiServices = ApiClient()
    val moshiBuilder: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val generalFunctions: GeneralFunctions = GeneralFunctions()
}