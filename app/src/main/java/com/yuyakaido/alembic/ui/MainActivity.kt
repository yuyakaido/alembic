package com.yuyakaido.alembic.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.lifecycleScope
import com.yuyakaido.alembic.domain.RepoRepository
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val uiState = mutableStateOf(MainUiState())
        lifecycleScope.launch {
            uiState.value = uiState.value.copy(isLoading = true)
            RepoRepository.searchAndroidRepositories()
                .onSuccess { repos ->
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        repos = repos,
                    )
                }
                .onFailure {
                    uiState.value = uiState.value.copy(isLoading = false)
                }
        }

        setContent {
            MaterialTheme {
                MainScreen(uiState = uiState.value)
            }
        }
    }
}
