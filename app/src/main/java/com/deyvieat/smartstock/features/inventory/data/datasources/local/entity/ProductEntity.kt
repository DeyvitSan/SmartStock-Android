package com.deyvieat.smartstock.features.inventory.data.datasources.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val price: Double,
    val quantity: Int,
    val lastSynced: Long = System.currentTimeMillis()
)
