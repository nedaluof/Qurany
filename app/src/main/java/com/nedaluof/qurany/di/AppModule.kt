package com.nedaluof.qurany.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.nedaluof.qurany.data.source.localsource.preferences.PreferencesManager
import com.nedaluof.qurany.data.source.localsource.preferences.PreferencesManagerImpl
import dagger.Binds
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
abstract class AppModule {

    @Binds
    abstract fun providePreferencesManager(
        preferencesManager: PreferencesManagerImpl
    ): PreferencesManager

    @InstallIn(SingletonComponent::class)
    @Module
    internal object DataStoreModule {
        private val Context.dataStore by preferencesDataStore(name = "QURANY_PREFERENCES")

        @Provides
        fun provideDataStore(
            @ApplicationContext
            context: Context
        ): DataStore<Preferences> = context.dataStore
    }
}
