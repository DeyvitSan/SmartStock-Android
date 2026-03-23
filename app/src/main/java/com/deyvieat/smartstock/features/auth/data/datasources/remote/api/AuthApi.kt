package com.deyvieat.smartstock.features.auth.data.datasources.remote.api

import com.deyvieat.smartstock.features.auth.data.datasources.remote.models.GenericResponseDto
import com.deyvieat.smartstock.features.auth.data.datasources.remote.models.LoginRequestDto
import com.deyvieat.smartstock.features.auth.data.datasources.remote.models.LoginResponseDto
import com.deyvieat.smartstock.features.auth.data.datasources.remote.models.RegisterRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto

    @POST("register")
    suspend fun register(@Body request: RegisterRequestDto): GenericResponseDto
}