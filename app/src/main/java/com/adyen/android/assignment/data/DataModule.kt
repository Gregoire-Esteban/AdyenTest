package com.adyen.android.assignment.data

import com.adyen.android.assignment.BuildConfig
import com.adyen.android.assignment.data.api.PlanetaryService
import com.adyen.android.assignment.data.api.model.DayAdapter
import com.adyen.android.assignment.domain.repositories.AstronomyRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val dataModule = module {
    single {
        HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }
    }
    single {
        Moshi
            .Builder()
            .add(DayAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    single { createRetrofit(get(), get()).create(PlanetaryService::class.java) }
    single { AstronomyRepository(get()) }
}

fun createClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient
        .Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}

fun createRetrofit(moshi: Moshi, loggingInterceptor: HttpLoggingInterceptor): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.NASA_BASE_URL)
        .client(createClient(loggingInterceptor))
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}