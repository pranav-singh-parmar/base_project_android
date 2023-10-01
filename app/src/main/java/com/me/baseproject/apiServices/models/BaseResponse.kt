package com.livevalue.customer.apiServices.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse(
    @Json(name = "success")
    val success: Boolean? = false,
    @Json(name = "status")
    val status: Int? = null,
    @Json(name = "message")
    val message: String? = null
)
