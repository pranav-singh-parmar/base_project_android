package com.me.baseproject.utils

class AppConfig {
    companion object {
        val DEBUG = false

        fun getBaseURL(): String {
//            if(BuildConfig.DEBUG) {
//                return ""
//            }

            return ""
        }
    }
}

class BaseURL {
    companion object {
        val baseURL = AppConfig.getBaseURL()
    }
}

class AppUrls {
    companion object {
        val apiURL = BaseURL.baseURL + "api/"
        val imageURL = BaseURL.baseURL
    }
}

class EndPoints {
    companion object{
        const val characters = "characters"
    }
}

enum class ApiStatus {
    NotHitOnce, IsBeingHit, ApiHitWithError, ApiHit
}