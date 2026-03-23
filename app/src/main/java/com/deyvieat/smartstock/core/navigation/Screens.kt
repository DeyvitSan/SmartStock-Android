package com.deyvieat.smartstock.core.navigation

import kotlinx.serialization.Serializable

@Serializable object Splash
@Serializable object Login
@Serializable object Register
@Serializable object InventoryHome
@Serializable object AddProduct
@Serializable data class EditProduct(val productId: Int)
@Serializable object Scanner      // F02
@Serializable object Dashboard    // F03
