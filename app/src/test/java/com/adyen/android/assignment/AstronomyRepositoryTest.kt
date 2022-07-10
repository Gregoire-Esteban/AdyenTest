package com.adyen.android.assignment

import com.adyen.android.assignment.data.api.AstronomyRemoteDataSource
import com.adyen.android.assignment.data.api.EmptyBodyException
import com.adyen.android.assignment.domain.model.AstronomyPicture
import com.adyen.android.assignment.domain.repositories.AstronomyRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AstronomyRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testReturnedListContainsOnlyPictures() = runTest {
        val result = AstronomyRepository(MockAstronomyRemoteDataSource()).getAstronomyImageList().getOrNull()
        assert(result?.all { it.mediaType == "image" } ?: false)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testSuccessIsReturnedWhenResponseIsSuccessful() = runTest {
        val sut = AstronomyRepository(MockAstronomyRemoteDataSource())
            .run { getAstronomyImageList() }
        assert(sut.isSuccess)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testErrorIsReturnedWhenResponseIsSuccessful() = runTest {
        val mockAstronomyRemoteDataSource = object : AstronomyRemoteDataSource {
            override suspend fun getAPODBatch(): List<AstronomyPicture> {
                throw EmptyBodyException()
            }
        }
        val sut = AstronomyRepository(mockAstronomyRemoteDataSource)
            .run { getAstronomyImageList() }
        assert(sut.isFailure)
    }
}