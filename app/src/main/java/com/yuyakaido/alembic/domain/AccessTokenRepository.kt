package com.yuyakaido.alembic.domain

import com.yuyakaido.alembic.data.GitHubLocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessTokenRepository @Inject constructor(
    private val local: GitHubLocalDataSource,
) {
    fun getAccessToken(): String? = local.getAccessToken()
}
