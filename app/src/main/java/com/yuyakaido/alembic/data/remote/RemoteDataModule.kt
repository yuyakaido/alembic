package com.yuyakaido.alembic.data.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    fun providesOkHttpClient(
        interceptor: AuthInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()

    @Provides
    fun providesConverterFactory(): Converter.Factory {
        val json = Json {
            ignoreUnknownKeys = true
        }
        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    fun providesGitHubAuthApi(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): GitHubAuthApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://github.com")
        .addConverterFactory(converterFactory)
        .build()
        .create(GitHubAuthApi::class.java)

    @Provides
    fun providesGitHubApi(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): GitHubApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.github.com")
        .addConverterFactory(converterFactory)
        .build()
        .create(GitHubApi::class.java)
}
