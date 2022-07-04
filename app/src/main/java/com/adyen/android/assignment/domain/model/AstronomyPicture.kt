package com.adyen.android.assignment.domain.model

import com.squareup.moshi.Json
import java.time.LocalDate

data class AstronomyPicture(
    val title: String,
    val explanation: String,
    val date: LocalDate, // todo : use as id for a possible db
    @Json(name = "hdurl") val hdUrl: String?,
    val url: String,
)
