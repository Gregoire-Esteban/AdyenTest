package com.adyen.android.assignment.ui.adapter

import com.adyen.android.assignment.domain.model.AstronomyPicture

interface AstronomyPictureItemCallback {
    fun onItemClicked(astronomyPicture: AstronomyPicture)
}