package com.adyen.android.assignment.domain.repositories

import com.adyen.android.assignment.data.api.AstronomyRemoteDataSource
import com.adyen.android.assignment.domain.model.AstronomyPicture
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AstronomyRepository(
    private val astronomyRemoteDataSource: AstronomyRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    /**
     * Get a sanitized list of [AstronomyPicture]
     */
    suspend fun getAstronomyImageList(): Result<List<AstronomyPicture>> {
        return withContext(dispatcher) {
            try {
                val filteredBatch = astronomyRemoteDataSource.getAPODBatch().filter {
                    it.mediaType == "image"
                }
                Result.success(filteredBatch)
            } catch (t: Throwable) {
                Result.failure(t)
            }
        }
    }
}