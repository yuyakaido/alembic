package com.yuyakaido.alembic.data

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object GitHubRemoteDataSource {
    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl("https://api.github.com")
        .build()
    private val api = retrofit.create(GitHubApi::class.java)

    suspend fun searchRepositories(query: String): Result<SearchRepositoryResponse> = runCatching {
        api.searchRepositories(query)
    }
}
