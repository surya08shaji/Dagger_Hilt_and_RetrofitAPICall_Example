package com.example.daggerartid.module

import com.example.daggerartid.data.MainRepository
import com.example.daggerartid.data.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)

interface RepositoriesModule {
    @Binds
    fun mainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository
}