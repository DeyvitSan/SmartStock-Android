package com.deyvieat.smartstock.features.auth.data.datasources.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session")
data class SessionEntity(
    @PrimaryKey val id: Int = 1,
    val email: String,
    val isLoggedIn: Boolean = true
)