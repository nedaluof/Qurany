package com.nedaluof.qurany.di

import com.nedaluof.qurany.data.repoImpl.MyRecitersRepositoryImpl
import com.nedaluof.qurany.data.repoImpl.RecitersRepositoryImpl
import com.nedaluof.qurany.data.repoImpl.SplashRepositoryImpl
import com.nedaluof.qurany.data.repoImpl.SurasRepositoryImpl
import com.nedaluof.qurany.domain.repositories.MyRecitersRepository
import com.nedaluof.qurany.domain.repositories.RecitersRepository
import com.nedaluof.qurany.domain.repositories.SplashRepository
import com.nedaluof.qurany.domain.repositories.SurasRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by NedaluOf on 8/16/2021.
 */
@ExperimentalCoroutinesApi
@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoriesModule {

    @ViewModelScoped
    @Binds
    abstract fun bindSplashRepository(
        repoImpl: SplashRepositoryImpl
    ): SplashRepository

    @ViewModelScoped
    @Binds
    abstract fun bindRecitersRepository(
        repoImpl: RecitersRepositoryImpl
    ): RecitersRepository

    @ViewModelScoped
    @Binds
    abstract fun bindMyRecitersRepository(
        repoImpl: MyRecitersRepositoryImpl
    ): MyRecitersRepository

    @ViewModelScoped
    @Binds
    abstract fun bindSurasRepository(
        repoImpl: SurasRepositoryImpl
    ): SurasRepository
}