package com.example.myapplication.data.di

import com.example.myapplication.data.BreedService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Provides
    fun providesOkHttp() = OkHttpClient.Builder().build()

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl("https://dog.ceo/api/")
            .build()

    @Provides
    fun provideService(retrofit: Retrofit): BreedService =
        retrofit.create(BreedService::class.java)
}