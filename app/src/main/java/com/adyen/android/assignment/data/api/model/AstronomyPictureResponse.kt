package com.adyen.android.assignment.data.api.model

import com.squareup.moshi.Json
import java.time.LocalDate

data class AstronomyPictureResponse(
    val title: String,
    val explanation: String,
    val date: LocalDate,
    @Json(name = "media_type") val mediaType: String,
    @Json(name = "hdurl") val hdUrl: String?,
    val url: String,
)