package com.deyvieat.smartstock.features.inventory.data.datasources.remote.mapper

import com.deyvieat.smartstock.features.inventory.data.datasources.remote.models.ProductDto
import com.deyvieat.smartstock.features.inventory.domain.entities.Product

fun ProductDto.toDomain(): Product = Product(id = id, name = name, price = price, quantity = quantity)

fun Product.toDto(userId: Int = 1): ProductDto = ProductDto(
    id = id, name = name, price = price, quantity = quantity, userId = userId
)
