package com.livevalue.customer.apiServices.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.me.baseproject.apiServices.models.AnimeModel
import com.me.baseproject.apiServices.models.AnimeResponse
import com.me.baseproject.utils.ApiStatus
import com.me.baseproject.utils.EndPoints
import com.me.baseproject.utils.Singleton

class AnimeListViewModel : ViewModel() {

    val getAnimeListsAS: MutableLiveData<ApiStatus> =
        MutableLiveData<ApiStatus>(ApiStatus.NotHitOnce)
    val animeList: MutableList<AnimeModel?> = mutableListOf()

    private var totalPage = 0
    private var currentLength = 0
    private var currentPage = 0
    var lastIndex = 0

    private val fetchedAllData: Boolean
        get() {
            return totalPage <= currentPage
        }

    fun pagination(context: Context, index: Int) {
        if (getAnimeListsAS.value != ApiStatus.IsBeingHit && index == currentLength - 1 && !fetchedAllData) {
            getAnimeList(context, false)
        }
    }

    fun getAnimeList(context: Context, clearList: Boolean = true) {

        getAnimeListsAS.value = ApiStatus.IsBeingHit

        if (clearList) {
            currentPage = 1
        }

        val params = mapOf<String, Any>(
            "size" to "10",
            "search" to "Dragon Ball Z",
            "page" to currentPage
        )

        Singleton.apiServices.getApi(context,
            EndPoints.anime,
            true,
            params,
            AnimeResponse::class.java,
            { animeResponse, _ ->
                if (animeResponse != null) {

                    if (clearList) {
                        animeList.clear()
                    }

//                    total = animeResponse.total ?: 0

                    currentPage++
                    lastIndex = animeList.size
                    animeList.addAll(animeResponse.data ?: mutableListOf())
                    currentLength = animeList.size
                    totalPage = animeResponse.meta?.totalPage ?: 0

                    getAnimeListsAS.value = ApiStatus.ApiHit
                } else {
                    getAnimeListsAS.value = ApiStatus.ApiHitWithError
                }
            },
            {
                getAnimeListsAS.value = ApiStatus.ApiHitWithError
            })
    }
}