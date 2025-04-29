package com.example.myapplication.data

import javax.inject.Inject

interface BreedsRepository {
    suspend fun getBreeds(): List<String>
    suspend fun getBreedGallery(breed: String): List<String>
}

class UserRepositoryImpl @Inject constructor(val breedService: BreedService) : BreedsRepository {

    override suspend fun getBreeds(): List<String> =
        breedService.getBreeds().message.entries.map { it.key }

    override suspend fun getBreedGallery(breed: String): List<String> =
        breedService.getBreedGallery(breed).message
}