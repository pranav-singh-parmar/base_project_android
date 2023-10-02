package com.me.baseproject.apiServices.models
data class AnimeResponse(
    val data: List<AnimeModel?>?,
    val meta: MetaModel?
)

data class AnimeModel(
    val _id: String?,
    val alternativeTitles: List<String?>?,
    val episodes: Int?,
    val genres: List<String?>?,
    val hasEpisode: Boolean?,
    val hasRanking: Boolean?,
    val image: String?,
    val link: String?,
    val ranking: Int?,
    val status: String?,
    val synopsis: String?,
    val thumb: String?,
    val title: String?,
    val type: String?
)

data class MetaModel(
    val page: Int?,
    val size: Int?,
    val totalData: Int?,
    val totalPage: Int?
)