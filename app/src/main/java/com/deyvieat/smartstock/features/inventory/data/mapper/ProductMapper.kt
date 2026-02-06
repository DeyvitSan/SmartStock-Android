package com.deyvieat.smartstock.features.inventory.data.mapper

import com.deyvieat.smartstock.core.network.ProductDto
import com.deyvieat.smartstock.features.inventory.domain.entity.Product

// Convierte lo que llega de internet (DTO) a lo que usa la App (Entity)
fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        name = name,
        price = price,
        quantity = quantity
    )
}

// Convierte de la App (Entity) a lo que se envía a internet (DTO)
fun Product.toDto(userId: Int): ProductDto {
    return ProductDto(
        id = id,
        name = name,
        price = price,
        quantity = quantity,
        userId = userId
    )
}