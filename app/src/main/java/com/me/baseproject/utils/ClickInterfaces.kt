package com.me.baseproject.utils

import com.me.baseproject.apiServices.models.AnimeModel
interface AnimeAdapterInterface {
    fun animeAdapterClicked(animeModel: AnimeModel?)
    fun visibleItemPosition(position: Int)
}