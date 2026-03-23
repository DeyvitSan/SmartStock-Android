package com.deyvieat.smartstock.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deyvieat.smartstock.features.auth.data.datasources.local.dao.SessionDao
import com.deyvieat.smartstock.features.auth.data.datasources.local.entity.SessionEntity
import com.deyvieat.smartstock.features.inventory.data.datasources.local.dao.ProductDao
import com.deyvieat.smartstock.features.inventory.data.datasources.local.entity.ProductEntity

@Database(
    entities = [ProductEntity::class, SessionEntity::class],
    version = 2,
    exportSchema = false
)
abstract class SmartStockDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun sessionDao(): SessionDao
}