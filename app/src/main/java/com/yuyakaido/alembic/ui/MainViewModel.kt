package com.yuyakaido.alembic.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuyakaido.alembic.domain.RepoRepository
import com.yuyakaido.alembic.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

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
            MainTab.Me -> updateMeTab()
        }
    }

    fun onCompleteAuth(uri: Uri) {
        viewModelScope.launch {
            val code = uri.getQueryParameter("code") ?: return@launch
            userRepository.generateAccessToken(code)
                .onSuccess {
                    updateMeTab()
                }
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
            repoRepository.searchAndroidRepositories()
                .onSuccess { repos ->
                    _uiState.update {
                        it.copy(
                            repoTabState = it.repoTabState.copy(
                                isLoading = false,
                                repos = repos,
                            ),
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
            userRepository.searchUsers()
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

    private fun updateMeTab() {
        viewModelScope.launch {
            when (uiState.value.meTabState.state) {
                is MeTabState.State.Initial -> {
                    if (userRepository.isSignedIn()) {
                        userRepository.getMe()
                            .onSuccess { me ->
                                _uiState.update {
                                    it.copy(
                                        meTabState = it.meTabState.copy(
                                            state = MeTabState.State.SignedIn(me),
                                        ),
                                    )
                                }
                            }
                            .onFailure {
                                _uiState.update {
                                    it.copy(
                                        meTabState = it.meTabState.copy(
                                            state = MeTabState.State.Initial,
                                        ),
                                    )
                                }
                            }
                    } else {
                        _uiState.update {
                            it.copy(
                                meTabState = it.meTabState.copy(
                                    state = MeTabState.State.NotSignedIn(
                                        uri = userRepository.getAuthUri(),
                                    ),
                                ),
                            )
                        }
                    }
                }
                is MeTabState.State.NotSignedIn -> {
                    if (userRepository.isSignedIn()) {
                        userRepository.getMe()
                            .onSuccess { me ->
                                _uiState.update {
                                    it.copy(
                                        meTabState = it.meTabState.copy(
                                            state = MeTabState.State.SignedIn(me),
                                        ),
                                    )
                                }
                            }
                            .onFailure {
                                _uiState.update {
                                    it.copy(
                                        meTabState = it.meTabState.copy(
                                            state = MeTabState.State.Initial,
                                        ),
                                    )
                                }
                            }
                    }
                }
                is MeTabState.State.SignedIn -> Unit
            }
        }
    }
}
