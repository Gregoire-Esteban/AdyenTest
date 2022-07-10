package com.adyen.android.assignment.data.api

import com.adyen.android.assignment.domain.model.AstronomyPicture

interface AstronomyRemoteDataSource {
    suspend fun getAPODBatch(): List<AstronomyPicture>
}