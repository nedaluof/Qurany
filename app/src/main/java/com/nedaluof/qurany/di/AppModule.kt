package com.nedaluof.qurany.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Created by nedaluof on 12/11/2020.
 * Updated by nedaluof on 9/18/2021.
 */
@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    private const val QURANY_PREFERENCES = "QURANY_PREFERENCES"
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = QURANY_PREFERENCES)

    @Provides
    fun provideDataStore(
        @ApplicationContext
        context: Context
    ): DataStore<Preferences> = context.dataStore


    // TODO: inject firebase
}
