package com.yuyakaido.alembic.ui

import com.yuyakaido.alembic.domain.Repo

data class MainUiState(
    val isLoading: Boolean = false,
    val repos: List<Repo> = emptyList(),
)
