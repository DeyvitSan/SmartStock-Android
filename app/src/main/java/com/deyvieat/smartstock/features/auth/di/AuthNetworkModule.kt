package com.deyvieat.smartstock.features.auth.di

import com.deyvieat.smartstock.core.di.SmartStockRetrofit
import com.deyvieat.smartstock.features.auth.data.datasources.remote.api.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthNetworkModule {

    @Provides
    @Singleton
    fun provideAuthApi(@SmartStockRetrofit retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)
}

