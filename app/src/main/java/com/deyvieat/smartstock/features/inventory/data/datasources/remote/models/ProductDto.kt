package com.deyvieat.smartstock.features.inventory.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

data class ProductDto(
    val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Int,
    @SerializedName("user_id") val userId: Int
)

data class GenericResponseDto(val success: Boolean, val message: String? = null)
