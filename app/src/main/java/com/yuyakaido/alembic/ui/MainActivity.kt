package com.yuyakaido.alembic.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.lifecycleScope
import com.yuyakaido.alembic.domain.RepoRepository
import com.yuyakaido.alembic.domain.UserRepository
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val uiState = mutableStateOf(MainUiState())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                MainScreen(
                    uiState = uiState.value,
                    onClickTab = {
                        uiState.value = uiState.value.copy(selectedTab = it)
                        when (it) {
                            MainTab.Repo -> updateRepoTab()
                            MainTab.User -> updateUserTab()
                        }
                    },
                )
            }
        }
        updateRepoTab()
    }

    private fun updateRepoTab() {
        if (uiState.value.repoTabState.isLoading) {
            return
        }
        if (uiState.value.repoTabState.repos.isNotEmpty()) {
            return
        }

        lifecycleScope.launch {
            uiState.value = uiState.value.copy(
                repoTabState = uiState.value.repoTabState.copy(isLoading = true),
            )
            RepoRepository.searchAndroidRepositories()
                .onSuccess { repos ->
                    uiState.value = uiState.value.copy(
                        repoTabState = uiState.value.repoTabState.copy(
                            isLoading = false,
                            repos = repos,
                        ),
                    )
                }
                .onFailure {
                    uiState.value = uiState.value.copy(
                        repoTabState = uiState.value.repoTabState.copy(isLoading = false),
                    )
                }
        }
    }

    private fun updateUserTab() {
        if (uiState.value.userTabState.isLoading) {
            return
        }
        if (uiState.value.userTabState.users.isNotEmpty()) {
            return
        }

        lifecycleScope.launch {
            uiState.value = uiState.value.copy(
                userTabState = uiState.value.userTabState.copy(isLoading = true),
            )
            UserRepository.searchUsers()
                .onSuccess { users ->
                    uiState.value = uiState.value.copy(
                        userTabState = uiState.value.userTabState.copy(
                            isLoading = false,
                            users = users,
                        ),
                    )
                }
                .onFailure {
                    uiState.value = uiState.value.copy(
                        userTabState = uiState.value.userTabState.copy(isLoading = false),
                    )
                }
        }
    }
}
