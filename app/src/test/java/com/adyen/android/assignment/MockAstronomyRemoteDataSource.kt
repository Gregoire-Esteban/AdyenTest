package com.adyen.android.assignment

import com.adyen.android.assignment.data.api.AstronomyRemoteDataSource
import com.adyen.android.assignment.domain.model.AstronomyPicture
import java.time.LocalDate

class MockAstronomyRemoteDataSource : AstronomyRemoteDataSource {

    companion object {
        private const val EMPTY_STRING = ""
    }

    override suspend fun getAPODBatch(): List<AstronomyPicture> {
        return listOf(
            AstronomyPicture(
                EMPTY_STRING,
                EMPTY_STRING,
                LocalDate.now(),
                null,
                "image",
                EMPTY_STRING
            ),
            AstronomyPicture(
                EMPTY_STRING,
                EMPTY_STRING,
                LocalDate.now(),
                null,
                "video",
                EMPTY_STRING
            )
        )
    }
}