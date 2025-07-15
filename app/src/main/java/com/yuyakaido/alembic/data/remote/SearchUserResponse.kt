package com.yuyakaido.alembic.data.remote

import com.yuyakaido.alembic.domain.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchUserResponse(
    @SerialName("items") val items: List<UserResponse>,
) {
    fun toUsers(): List<User> = items.map { it.toUser() }
}
