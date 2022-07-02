package com.icdominguez.starwarsencyclopedia.di

import com.icdominguez.starwarsencyclopedia.data.repository.GenericStarWarsRepository
import com.icdominguez.starwarsencyclopedia.data.common.Constants
import com.icdominguez.starwarsencyclopedia.data.network.ApiClient
import com.icdominguez.starwarsencyclopedia.data.network.ApiService
import com.icdominguez.starwarsencyclopedia.data.repository.StarWarsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun providesApiClient(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideStarWarsRepositoryImpl(apiClient: ApiClient): StarWarsRepository = GenericStarWarsRepository(apiClient)
}