package com.yuyakaido.alembic.data

import com.yuyakaido.alembic.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object GitHubRemoteDataSource {
    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.GITHUB_PERSONAL_ACCESS_TOKEN}")
                    .build()
            )
        }
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.github.com")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
    private val api = retrofit.create(GitHubApi::class.java)

    suspend fun searchRepositories(query: String): Result<SearchRepositoryResponse> = runCatching {
        api.searchRepositories(query)
    }

    suspend fun searchUsers(query: String): Result<SearchUserResponse> = runCatching {
        api.searchUsers(query)
    }

    suspend fun getMe(): Result<UserResponse> = runCatching {
        api.getMe()
    }
}
