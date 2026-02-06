package com.deyvieat.smartstock.core.navigation

import kotlinx.serialization.Serializable

//rutas de la app

@Serializable
object Login

@Serializable
object Register

@Serializable
object InventoryHome

@Serializable
object AddProduct

@Serializable
data class EditProduct(val productId: Int)