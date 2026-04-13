package com.deyvieat.smartstock.features.notifications.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.deyvieat.smartstock.features.notifications.domain.entities.NotificationItem
import com.deyvieat.smartstock.features.notifications.presentation.screens.NotificationsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsUiState())
    val uiState = _uiState.asStateFlow()

    private val _notifications = mutableListOf<NotificationItem>()

    fun addNotification(productName: String, quantity: Int) {
        _notifications.add(
            0,
            NotificationItem(
                id = _notifications.size + 1,
                productName = productName,
                quantity = quantity
            )
        )
        _uiState.update {
            it.copy(
                notifications = _notifications.toList(),
                isEmpty = false
            )
        }
    }
}