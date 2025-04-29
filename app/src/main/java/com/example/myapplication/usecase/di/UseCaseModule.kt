package com.example.myapplication.usecase.di

import com.example.myapplication.usecase.FetchAllBreedsUseCase
import com.example.myapplication.usecase.FetchAllBreedsUseCaseImpl
import com.example.myapplication.usecase.FetchBreedGalleryUseCase
import com.example.myapplication.usecase.FetchBreedGalleryUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule() {

    @Binds
    abstract fun bindFetchAllBreedsUseCase(useCase: FetchAllBreedsUseCaseImpl): FetchAllBreedsUseCase

    @Binds
    abstract fun bindFetchBreedGalleryUseCase(useCase: FetchBreedGalleryUseCaseImpl): FetchBreedGalleryUseCase

}