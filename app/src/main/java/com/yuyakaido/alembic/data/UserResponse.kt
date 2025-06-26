package com.yuyakaido.alembic.data

import com.yuyakaido.alembic.domain.Me
import com.yuyakaido.alembic.domain.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("id") val id: Long,
    @SerialName("login") val login: String,
) {
    fun toUser(): User = User(id = id, name = login)
    fun toMe(): Me = Me(id = id, name = login)
}
