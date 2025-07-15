package com.yuyakaido.alembic.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerateAccessTokenResponse(
    @SerialName("access_token") val accessToken: String,
)
