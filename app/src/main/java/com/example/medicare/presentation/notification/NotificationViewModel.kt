package com.example.medicare.presentation.notification

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(

):ViewModel() {
    private val _uiState= MutableStateFlow(NotificationUiState())
    val uiState=_uiState.asStateFlow()
}