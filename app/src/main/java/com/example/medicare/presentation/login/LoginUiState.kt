package com.example.medicare.presentation.login

import com.example.medicare.R
import com.example.medicare.data.model.result.AuthState
import com.google.rpc.context.AttributeContext.Auth

data class LoginUiState(
    val email:String="",
    val emailErrorMessage:Int?= R.string.blank,
    val password: String="",
    val isPasswordVisible: Boolean=false,
    val passwordErrorMessage: Int?=R.string.blank,
    val acceptPrivacyIsChecked:Boolean=false,
    val showLoadingDialog:Boolean=false,
    val showErrorDialog:Boolean=false,
    val authState:AuthState=AuthState.Loading,
)