package com.deyvieat.smartstock.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @SmartStockRetrofit
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://unenthralling-armandina-memorizable.ngrok-free.dev/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}