package com.hyunjung.feature.auth.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyunjung.core.common.util.DataError
import com.hyunjung.core.common.util.Result
import com.hyunjung.core.domain.repository.AuthRepository
import com.hyunjung.core.model.LoginResult
import com.hyunjung.core.model.SocialType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LogInViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(context: Context, socialType: SocialType) {
        viewModelScope.launch {
            try {
                _uiState.update { LoginUiState.Loading }
                when (val result = authRepository.login(context, socialType).first()) {
                    is Result.Success -> _uiState.update { LoginUiState.Success(result.data) }
                    is Result.Error -> _uiState.update { LoginUiState.Error(result.error) }
                }
            } catch (e: Exception) {
                _uiState.update { LoginUiState.Error(DataError.Network.UNKNOWN) }
            }
        }
    }
}

sealed interface LoginUiState {
    object Idle : LoginUiState
    object Loading : LoginUiState
    data class Success(val loginResult: LoginResult) : LoginUiState
    data class Error(val error: DataError) : LoginUiState
}