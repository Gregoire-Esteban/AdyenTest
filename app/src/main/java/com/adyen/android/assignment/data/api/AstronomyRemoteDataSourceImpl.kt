package com.adyen.android.assignment.data.api

import com.adyen.android.assignment.domain.model.AstronomyPicture

class AstronomyRemoteDataSourceImpl(
    private val planetaryService: PlanetaryService
) : AstronomyRemoteDataSource {

    override suspend fun getAPODBatch(): List<AstronomyPicture> = request(
        { planetaryService.getPictures() }
    ) {
        it.map { dto ->
            AstronomyPicture(
                dto.title,
                dto.explanation,
                dto.date,
                dto.hdUrl,
                dto.mediaType,
                dto.url
            )
        }
    }
}