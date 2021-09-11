package com.nedaluof.qurany.di

import android.content.Context
import com.nedaluof.qurany.data.localsource.prefs.PreferencesHelper
import com.nedaluof.qurany.data.localsource.room.ReciterDao
import com.nedaluof.qurany.data.remotesource.api.ApiService
import com.nedaluof.qurany.data.repoImpl.MyRecitersRepositoryImpl
import com.nedaluof.qurany.data.repoImpl.RecitersRepositoryImpl
import com.nedaluof.qurany.data.repoImpl.SplashRepositoryImpl
import com.nedaluof.qurany.data.repoImpl.SurasRepositoryImpl
import com.nedaluof.qurany.domain.repositories.MyRecitersRepository
import com.nedaluof.qurany.domain.repositories.RecitersRepository
import com.nedaluof.qurany.domain.repositories.SplashRepository
import com.nedaluof.qurany.domain.repositories.SurasRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Created by NedaluOf on 8/16/2021.
 */
@InstallIn(ViewModelComponent::class)
@Module
object RepositoriesModule {

    @ViewModelScoped
    @Provides
    fun provideRecitersRepository(
        apiService: ApiService,
        preferences: PreferencesHelper,
        reciterDao: ReciterDao,
    ): RecitersRepository = RecitersRepositoryImpl(
        apiService,
        preferences,
        reciterDao
    )

    @ViewModelScoped
    @Provides
    fun provideSurasRepository(
        @ApplicationContext context: Context,
    ): SurasRepository = SurasRepositoryImpl(context)


    @ViewModelScoped
    @Provides
    fun provideSplashRepository(
        @ApplicationContext context: Context,
    ): SplashRepository = SplashRepositoryImpl(context)


    @ViewModelScoped
    @Provides
    fun provideMyRecitersRepository(
        recitersDao: ReciterDao,
        preferences: PreferencesHelper,
    ): MyRecitersRepository = MyRecitersRepositoryImpl(recitersDao, preferences)

}