package com.livevalue.customer.apiServices.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.me.baseproject.apiServices.models.AnimeModel
import com.me.baseproject.utils.ApiStatus
import com.me.baseproject.utils.EndPoints
import com.me.baseproject.utils.Singleton

class AnimeListViewModel : ViewModel() {

    val getAnimeListsAS: MutableLiveData<ApiStatus> =
        MutableLiveData<ApiStatus>(ApiStatus.NotHitOnce)
    val animeList: MutableList<AnimeModel?> = mutableListOf()

    private var total = 0
    private var currentLength = 0
    var lastIndex = 0

    private val fetchedAllData: Boolean
        get() {
            return total <= currentLength
        }

    fun pagination(context: Context, index: Int) {
        if (getAnimeListsAS.value != ApiStatus.IsBeingHit && index == currentLength - 1 && !fetchedAllData) {
            getRechargePackages(context, false)
        }
    }

    fun getRechargePackages(context: Context, clearList: Boolean = true) {

        getAnimeListsAS.value = ApiStatus.IsBeingHit

        if (clearList) {
            currentLength = 0
        }

        val params = mapOf<String, Any>(
            "limit" to 10,
            "offset" to currentLength
        )

        Singleton.apiServices.postApi(context,
            EndPoints.characters,
            true,
            params,
            AnimeModel::class.java,
            { animeResponse, _ ->
//                if (animeResponse != null && animeResponse.success == true) {
//
//                    if (clearList) {
//                        animeList.clear()
//                    }
//
////                    total = animeResponse.total ?: 0
//
//                    lastIndex = animeList.size
////                    animeList.addAll(animeResponse.data ?: mutableListOf())
//                    currentLength = animeList.size
//
//                    getAnimeListsAS.value = ApiStatus.ApiHit
//                } else {
//                    getAnimeListsAS.value = ApiStatus.ApiHitWithError
//                }
            },
            {
                getAnimeListsAS.value = ApiStatus.ApiHitWithError
            })
    }
}