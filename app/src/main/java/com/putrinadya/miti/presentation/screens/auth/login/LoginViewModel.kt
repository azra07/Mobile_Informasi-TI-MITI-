package com.putrinadya.miti.presentation.screens.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.putrinadya.miti.domain.model.User
import com.putrinadya.miti.domain.usecase.auth.CheckSessionUseCase
import com.putrinadya.miti.domain.usecase.auth.ForgotPasswordUseCase
import com.putrinadya.miti.domain.usecase.auth.LoginUseCase
import com.putrinadya.miti.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val checkSessionUseCase: CheckSessionUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onEmailOrNimChanged(newValue: String) {
        uiState = uiState.copy(emailOrNim = newValue, error = null)
    }

    fun onPasswordChanged(newValue: String) {
        uiState = uiState.copy(password = newValue, error = null)
    }

    fun login(role: String) {
        if (uiState.emailOrNim.isBlank() || uiState.password.isBlank()) {
            uiState = uiState.copy(error = "Mohon Isi Semua Kolom")
            return
        }

        uiState = uiState.copy(isLoading = true, error = null)

        // Simulasi login sukses berdasarkan role yang dipilih
        viewModelScope.launch {
            loginUseCase(uiState.emailOrNim, uiState.password).collect { result ->
                result.onSuccess { user ->
                    if (user.role == role) {
                        uiState = uiState.copy(
                            isLoading = false,
                            isSuccess = true,
                            userRole = user.role
                        )
                    } else {
                        uiState = uiState.copy(
                            isLoading = false,
                            error = "Akses ditolak. Anda bukan $role"
                        )
                    }
                }
                result.onFailure { exception ->
                    uiState = uiState.copy(
                        isLoading = false,
                        error = exception.message ?: "Login gagal. Periksa kembali email atau password Anda."
                    )
                }
            }
        }
    }

    fun sendForgotPassword(email: String) {
        if (email.isBlank()) {
            uiState = uiState.copy(error = "Masukkan email terlebih dahulu")
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            // Gunakan repository untuk mengirim email
            forgotPasswordUseCase(email).collect { result ->
                uiState = if (result.isSuccess) {
                    uiState.copy(
                        isLoading = false,
                        error = "Link reset password telah dikirim ke email"
                    )
                } else {
                    uiState.copy(isLoading = false, error = result.exceptionOrNull()?.message)
                }
            }
        }
    }

    suspend fun checkUserSession(): String {
        val user: User? = checkSessionUseCase()
        return if (user == null) {
            Screen.Login.route
        } else {
            if (user.role == "admin") {
                Screen.AdminDashboard.route
            } else {
                Screen.StudentDashboard.route
            }
        }
    }

    fun resetSuccessState() {
        uiState = uiState.copy(isSuccess = false)
    }
}