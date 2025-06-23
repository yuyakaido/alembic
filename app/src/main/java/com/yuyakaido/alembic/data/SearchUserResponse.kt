package com.yuyakaido.alembic.data

import com.yuyakaido.alembic.domain.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchUserResponse(
    @SerialName("items") val items: List<UserResponse>,
) {
    fun toUsers(): List<User> = items.map {
        User(
            id = it.id,
            name = it.login,
        )
    }
}

@Serializable
data class UserResponse(
    @SerialName("id") val id: Long,
    @SerialName("login") val login: String,
)
