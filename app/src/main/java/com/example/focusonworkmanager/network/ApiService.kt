package com.example.focusonworkmanager.network

import com.example.focusonworkmanager.network.models.ProductsApiResponseItem
import com.example.focusonworkmanager.network.models.UsersApiResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("products")
    suspend fun getProducts(): Response<List<ProductsApiResponseItem>>

    @GET("users")
    suspend fun getUsers(): Response<List<UsersApiResponseItem>>

}