package com.example.medicare.presentation.doctor.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicare.data.services.AccountService
import com.example.medicare.ui.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()


    fun updateEmail(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail, emailErrorMessage = null)
    }

    fun updatePassword(newPassword: String) {
        _uiState.value =
            _uiState.value.copy(password = newPassword, passwordErrorMessage = null)
    }

    fun updatePasswordVisibilityState() {
        _uiState.value =
            _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
    }

    fun updateCheckState(newState: Boolean) {
        _uiState.value =
            _uiState.value.copy(acceptPrivacyIsChecked = newState)
    }

    fun updateErrorDialogVisibilityState() {
        _uiState.value =
            _uiState.value.copy(showErrorDialog = !uiState.value.showErrorDialog)
    }
    fun updateLoadingDialogVisibilityState() {
        _uiState.value =
            _uiState.value.copy(showLoadingDialog = !uiState.value.showLoadingDialog)
    }

    fun login(onClickLoginButton:()->Unit) {
        _uiState.value=
            _uiState.value.copy(
                emailErrorMessage = Validator.checkEmail(uiState.value.email),
                passwordErrorMessage = Validator.checkPassword(uiState.value.password),
            )
        if(uiState.value.emailErrorMessage==null&&
            uiState.value.passwordErrorMessage==null&&
            uiState.value.acceptPrivacyIsChecked
        ){
            viewModelScope.launch {
                try {
                    updateLoadingDialogVisibilityState()
                    accountService.login(uiState.value.email,uiState.value.password)
                    updateLoadingDialogVisibilityState()
                    onClickLoginButton()
                }catch (e:Exception){
                    updateLoadingDialogVisibilityState()
                    updateErrorDialogVisibilityState()
                    Log.e("Log in",e.message?:"Error")
                }
            }
        }
    }
}