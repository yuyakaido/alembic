package com.yuyakaido.alembic.data.remote

import com.yuyakaido.alembic.domain.Repo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchRepositoryResponse(
    @SerialName("items") val items: List<RepoRepositoryResponse>,
) {
    fun toRepos(): List<Repo> = items.map {
        Repo(
            id = it.id,
            fullName = it.fullName,
        )
    }
}

@Serializable
data class RepoRepositoryResponse(
    @SerialName("id") val id: Long,
    @SerialName("full_name") val fullName: String,
)
