package com.me.baseproject.utils

class AppConfig {
    companion object {
        val DEBUG = false

        fun getBaseURL(): String {
//            if(BuildConfig.DEBUG) {
//                return ""
//            }

            return "https://anime-db.p.rapidapi.com/"
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
        val apiURL = BaseURL.baseURL
        val imageURL = BaseURL.baseURL
    }
}

class EndPoints {
    companion object{
        const val anime = "anime"
    }
}

enum class ApiStatus {
    NotHitOnce, IsBeingHit, ApiHitWithError, ApiHit
}