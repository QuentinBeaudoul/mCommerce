package com.beaudoul.mcommerce.api

import com.beaudoul.mcommerce.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/android-hiring-test/products1627044891.json")
    suspend fun getProduct(): Response<MutableList<Product>>
}