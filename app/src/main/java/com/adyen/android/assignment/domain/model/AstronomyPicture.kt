package com.adyen.android.assignment.domain.model

import java.time.LocalDate

data class AstronomyPicture(
    val title: String,
    val explanation: String,
    val date: LocalDate, // todo : use as id for a possible db
    val hdUrl: String?,
    val mediaType: String,
    val url: String,
    val isPinned: Boolean = false
)
