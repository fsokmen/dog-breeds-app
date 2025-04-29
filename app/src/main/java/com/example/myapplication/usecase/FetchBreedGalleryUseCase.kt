package com.example.myapplication.usecase

import com.example.myapplication.data.BreedsRepository
import javax.inject.Inject

interface FetchBreedGalleryUseCase {
    suspend fun execute(breed: String): List<String>
}

class FetchBreedGalleryUseCaseImpl @Inject constructor(val breedsRepository: BreedsRepository) :
    FetchBreedGalleryUseCase {

    override suspend fun execute(breed: String): List<String> {
        return breedsRepository.getBreedGallery(breed).shuffled().take(10)
    }
}