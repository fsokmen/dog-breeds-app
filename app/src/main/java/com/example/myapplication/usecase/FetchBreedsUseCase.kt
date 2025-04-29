package com.example.myapplication.usecase

import com.example.myapplication.data.BreedsRepository
import javax.inject.Inject

interface FetchAllBreedsUseCase {
    suspend fun execute(): List<String>
}

class FetchAllBreedsUseCaseImpl @Inject constructor(val breedsRepository: BreedsRepository) :
    FetchAllBreedsUseCase {

    override suspend fun execute(): List<String> {
        return breedsRepository.getBreeds()
    }
}