package com.deyvieat.smartstock.features.auth.di

import com.deyvieat.smartstock.core.database.SmartStockDatabase
import com.deyvieat.smartstock.features.auth.data.datasources.local.dao.SessionDao
import com.deyvieat.smartstock.features.auth.data.repository.AuthRepositoryImpl
import com.deyvieat.smartstock.features.auth.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDatabaseModule {

    @Provides
    @Singleton
    fun provideSessionDao(db: SmartStockDatabase): SessionDao = db.sessionDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}
