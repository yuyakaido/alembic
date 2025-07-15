package com.yuyakaido.alembic.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
    ): SearchRepositoryResponse

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
    ): SearchUserResponse

    @GET("user")
    suspend fun getMe(): UserResponse
}
