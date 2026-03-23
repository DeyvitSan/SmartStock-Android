package com.deyvieat.smartstock.features.inventory.data.datasources.local.mapper

import com.deyvieat.smartstock.features.inventory.data.datasources.local.entity.ProductEntity
import com.deyvieat.smartstock.features.inventory.domain.entities.Product

fun ProductEntity.toDomain(): Product = Product(id = id, name = name, price = price, quantity = quantity)

fun Product.toEntity(): ProductEntity = ProductEntity(id = id, name = name, price = price, quantity = quantity)
