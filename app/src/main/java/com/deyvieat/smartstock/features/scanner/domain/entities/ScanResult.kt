package com.deyvieat.smartstock.features.scanner.domain.entities

import com.deyvieat.smartstock.features.inventory.domain.entities.Product

data class ScanResult(val scannedCode: String, val matchedProducts: List<Product>)
