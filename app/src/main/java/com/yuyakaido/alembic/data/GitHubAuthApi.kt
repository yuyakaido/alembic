package com.yuyakaido.alembic.data

import retrofit2.http.POST
import retrofit2.http.Query

interface GitHubAuthApi {
    @POST("login/oauth/access_token")
    suspend fun generateAccessToken(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String,
    ): GenerateAccessTokenResponse
}
