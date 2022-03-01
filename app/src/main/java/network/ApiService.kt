package network

import network.models.ApiResponseItem
import network.models.UsersApiResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("products")
    suspend fun getProducts(): Response<List<ApiResponseItem>>

    @GET("users")
    suspend fun getUsers(): Response<List<UsersApiResponseItem>>

}