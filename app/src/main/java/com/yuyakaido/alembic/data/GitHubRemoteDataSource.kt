package com.yuyakaido.alembic.data

import android.net.Uri
import androidx.core.net.toUri
import com.yuyakaido.alembic.BuildConfig
import com.yuyakaido.alembic.domain.UserRepository
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object GitHubRemoteDataSource {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            runBlocking {
                chain.proceed(
                    chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .apply {
                            val accessToken = UserRepository.getAccessToken()
                            if (accessToken != null) {
                                addHeader("Authorization", "Bearer $accessToken")
                            }
                        }
                        .build()
                )
            }
        }
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val converterFactory = json.asConverterFactory("application/json".toMediaType())

    private val authApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://github.com")
        .addConverterFactory(converterFactory)
        .build()
        .create(GitHubAuthApi::class.java)
    private val api = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.github.com")
        .addConverterFactory(converterFactory)
        .build()
        .create(GitHubApi::class.java)

    fun getAuthUri(): Uri = "https://github.com/login/oauth/authorize".toUri()
        .buildUpon()
        .appendQueryParameter("client_id", BuildConfig.GITHUB_OAUTH_CLIENT_ID)
        .build()

    suspend fun generateAccessToken(code: String): Result<GenerateAccessTokenResponse> = runCatching {
        authApi.generateAccessToken(
            clientId = BuildConfig.GITHUB_OAUTH_CLIENT_ID,
            clientSecret = BuildConfig.GITHUB_OAUTH_CLIENT_SECRET,
            code = code,
        )
    }

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
