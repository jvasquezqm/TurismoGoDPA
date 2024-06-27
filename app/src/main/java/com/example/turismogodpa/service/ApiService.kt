package com.example.turismogodpa.service

import RucResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface ApiService {
    @GET("sunat/ruc/full")
    fun getRucInfo(
        @Query("numero") numero: String?,
        @Header("Authorization") authHeader: String?
    ): Call<RucResponse?>?
}