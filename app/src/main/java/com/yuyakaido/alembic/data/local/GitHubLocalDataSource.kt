package com.yuyakaido.alembic.data.local

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubLocalDataSource @Inject constructor(
    private val dataStore: DataStore<UserPreferences>,
) {

    suspend fun getAccessToken(): String? = dataStore.data
        .map { userPreferences ->
            val accessToken = userPreferences.accessToken
            if (accessToken.isNullOrEmpty()) {
                null
            } else {
                accessToken
            }
        }
        .firstOrNull()

    suspend fun updateAccessToken(accessToken: String) = dataStore
        .updateData { it.toBuilder().setAccessToken(accessToken).build() }
}
