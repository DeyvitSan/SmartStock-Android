package com.deyvieat.smartstock.features.notifications.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deyvieat.smartstock.features.notifications.domain.repository.NotificationsRepository
import com.deyvieat.smartstock.features.notifications.presentation.screens.NotificationsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val repository: NotificationsRepository
) : ViewModel() {

    val uiState = repository.notifications
        .map { list ->
            NotificationsUiState(
                notifications = list,
                isEmpty = list.isEmpty()
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NotificationsUiState())
}