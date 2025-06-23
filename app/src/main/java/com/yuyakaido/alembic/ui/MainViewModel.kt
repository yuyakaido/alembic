package com.yuyakaido.alembic.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuyakaido.alembic.domain.RepoRepository
import com.yuyakaido.alembic.domain.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MainUiState(),
    )

    init {
        updateRepoTab()
    }

    fun onClickTab(tab: MainTab) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
        when (tab) {
            MainTab.Repo -> updateRepoTab()
            MainTab.User -> updateUserTab()
        }
    }

    private fun updateRepoTab() {
        if (uiState.value.repoTabState.isLoading) {
            return
        }
        if (uiState.value.repoTabState.repos.isNotEmpty()) {
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(repoTabState = it.repoTabState.copy(isLoading = true))
            }
            RepoRepository.searchAndroidRepositories()
                .onSuccess { repos ->
                    _uiState.update {
                        it.copy(
                            repoTabState = it.repoTabState.copy(
                                isLoading = false,
                                repos = repos,
                            )
                        )
                    }
                }
                .onFailure {
                    _uiState.update {
                        it.copy(repoTabState = it.repoTabState.copy(isLoading = false))
                    }
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

        viewModelScope.launch {
            _uiState.update {
                it.copy(userTabState = it.userTabState.copy(isLoading = true))
            }
            UserRepository.searchUsers()
                .onSuccess { users ->
                    _uiState.update {
                        it.copy(
                            userTabState = it.userTabState.copy(
                                isLoading = false,
                                users = users,
                            ),
                        )
                    }
                }
                .onFailure {
                    _uiState.update {
                        it.copy(userTabState = it.userTabState.copy(isLoading = false))
                    }
                }
        }
    }
}
