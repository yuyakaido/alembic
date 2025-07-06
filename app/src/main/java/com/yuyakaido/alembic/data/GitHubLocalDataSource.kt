package com.yuyakaido.alembic.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubLocalDataSource @Inject constructor() {

    private val accessTokenStream = MutableStateFlow<String?>(null)

    fun getAccessToken(): String? = accessTokenStream.value

    fun updateAccessToken(accessToken: String) = accessTokenStream.update { accessToken }
}
