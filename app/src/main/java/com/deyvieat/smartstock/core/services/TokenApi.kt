package com.deyvieat.smartstock.core.services

import retrofit2.http.Body
import retrofit2.http.POST

data class TokenDto(val token: String)

interface TokenApi {
    @POST("save-token")
    suspend fun saveToken(@Body token: TokenDto)
}