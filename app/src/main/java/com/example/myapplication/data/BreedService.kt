package com.example.myapplication.data

import retrofit2.http.GET
import retrofit2.http.Path

interface BreedService {

    @GET("breeds/list/all")
    suspend fun getBreeds(): BreedsDto

    @GET("breed/{breed}/images")
    suspend fun getBreedGallery(@Path("breed") breed: String): BreedGalleryDto
}