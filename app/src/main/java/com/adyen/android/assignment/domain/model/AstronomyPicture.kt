package com.adyen.android.assignment.domain.model

import android.os.Parcelable
import com.adyen.android.assignment.ui.adapter.AdapterItem
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class AstronomyPicture(
    val title: String,
    val explanation: String,
    val date: LocalDate, // todo : use as id for a possible db
    val hdUrl: String?,
    val mediaType: String,
    val url: String,
    val isPinned: Boolean = false
) : AdapterItem, Parcelable
