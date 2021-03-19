package com.nedaluof.qurany.di

import android.content.Context
import com.nedaluof.qurany.data.prefs.PreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

/**
 * Created by nedaluof on 12/11/2020.
 */
@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun providePrefsHelper(@ApplicationContext context: Context) =
            PreferencesHelper(context)
}
