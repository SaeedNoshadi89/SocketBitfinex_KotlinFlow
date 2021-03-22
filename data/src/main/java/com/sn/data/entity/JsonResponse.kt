package com.sn.data.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JsonResponse(
    val event: String,
    val channel: String,
    val chanId: String,
    val pair: String
)