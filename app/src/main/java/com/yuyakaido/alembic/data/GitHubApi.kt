package com.yuyakaido.alembic.data

import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
    ): SearchRepositoryResponse
}
