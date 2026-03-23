package com.deyvieat.smartstock.features.inventory.domain.entities

import java.io.Serializable

data class Product(val id: Int, val name: String, val price: Double, val quantity: Int) : Serializable
