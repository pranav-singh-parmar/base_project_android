package com.me.baseproject.utils

import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.livevalue.customer.apiServices.ApiClient
import com.squareup.moshi.Moshi

// https://medium.com/kayvan-kaseb/singleton-pattern-in-kotlin-7a6b422acd81
object Singleton {
    init {
        println("Singleton class invoked.")
    }

    val apiServices = ApiClient()
    val moshiBuilder: Moshi = Moshi.Builder().build()
    val generalFunctions: GeneralFunctions = GeneralFunctions()
}