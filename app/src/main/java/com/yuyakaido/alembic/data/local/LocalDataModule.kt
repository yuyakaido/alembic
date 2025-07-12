package com.yuyakaido.alembic.data.local

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    private val Application.userPreferencesStore: DataStore<UserPreferences> by dataStore(
        fileName = "user_preferences",
        serializer = UserPreferencesSerializer
    )

    @Provides
    fun providesUserPreferencesDataStore(
        application: Application,
    ): DataStore<UserPreferences> = application.userPreferencesStore
}
