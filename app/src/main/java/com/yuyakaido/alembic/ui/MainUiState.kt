package com.yuyakaido.alembic.ui

import android.net.Uri
import com.yuyakaido.alembic.domain.Me
import com.yuyakaido.alembic.domain.Repo
import com.yuyakaido.alembic.domain.User

data class MainUiState(
    val selectedTab: MainTab = MainTab.Repo,
    val repoTabState: RepoTabState = RepoTabState(),
    val userTabState: UserTabState = UserTabState(),
    val meTabState: MeTabState = MeTabState(),
)

data class RepoTabState(
    val isLoading: Boolean = false,
    val repos: List<Repo> = emptyList(),
)

data class UserTabState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
)

data class MeTabState(
    val state: State = State.Initial,
) {
    sealed class State {
        data object Initial : State()
        data class NotSignedIn(val uri: Uri) : State()
        data class SignedIn(val me: Me) : State()
    }
}
