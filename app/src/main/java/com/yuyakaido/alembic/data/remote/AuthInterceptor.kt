package com.yuyakaido.alembic.data.remote

import com.yuyakaido.alembic.domain.AccessTokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val accessTokenRepository: AccessTokenRepository,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        chain.proceed(
            chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .apply {
                    val accessToken = accessTokenRepository.getAccessToken()
                    if (accessToken != null) {
                        addHeader("Authorization", "Bearer $accessToken")
                    }
                }
                .build()
        )
    }
}
