package com.yuyakaido.alembic.ui

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.yuyakaido.alembic.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen(
    uiState: MainUiState,
    onClickTab: (tab: MainTab) -> Unit,
    onClickAuth: (uri: Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
            )
        },
        bottomBar = {
            NavigationBar {
                MainTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = uiState.selectedTab == tab,
                        onClick = { onClickTab(tab) },
                        icon = {
                            Icon(
                                imageVector = when (tab) {
                                    MainTab.Repo -> Icons.Default.Storage
                                    MainTab.User -> Icons.Default.People
                                    MainTab.Me -> Icons.Default.AccountCircle
                                },
                                contentDescription = tab.name,
                            )
                        },
                        label = {
                            Text(text = tab.name)
                        }
                    )
                }
            }
        }
    ) { paddings ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings),
        ) {
            when (uiState.selectedTab) {
                MainTab.Repo -> RepoTab(uiState.repoTabState)
                MainTab.User -> UserTab(uiState.userTabState)
                MainTab.Me -> MeTab(
                    uiState = uiState.meTabState,
                    onClickAuth = onClickAuth,
                )
            }
        }
    }
}

@Composable
private fun RepoTab(
    uiState: RepoTabState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(
                    count = uiState.repos.size,
                    key = { uiState.repos[it].id },
                ) { index ->
                    Text(
                        text = uiState.repos[index].fullName,
                    )
                }
            }
        }
    }
}

@Composable
private fun UserTab(
    uiState: UserTabState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(
                    count = uiState.users.size,
                    key = { uiState.users[it].id },
                ) { index ->
                    Text(
                        text = uiState.users[index].name,
                    )
                }
            }
        }
    }
}

@Composable
private fun MeTab(
    uiState: MeTabState,
    onClickAuth: (uri: Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        when (val state = uiState.state) {
            is MeTabState.State.Initial -> Unit
            is MeTabState.State.NotSignedIn -> {
                Button(
                    onClick = { onClickAuth(state.uri) },
                ) {
                    Text(text = "Sign in with GitHub")
                }
            }
            is MeTabState.State.SignedIn -> {
                AsyncImage(
                    model = state.me.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                )
                Text(text = state.me.name)
            }
        }
    }
}
