package com.example.myapplication.data.di

import com.example.myapplication.data.BreedsRepository
import com.example.myapplication.data.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule() {

    @Binds
    abstract fun bindUserRepository(repository: UserRepositoryImpl): BreedsRepository
}