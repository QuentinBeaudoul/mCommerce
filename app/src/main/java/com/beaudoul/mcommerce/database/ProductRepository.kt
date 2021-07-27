package com.beaudoul.mcommerce.database

import androidx.annotation.WorkerThread
import com.beaudoul.mcommerce.api.ApiClient
import com.beaudoul.mcommerce.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductRepository(private val productDao: ProductDao) {
    val allProducts: Flow<List<Product>> = productDao.getAllProducts()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    @WorkerThread
    suspend fun update() {
        updateCall()
    }

    private suspend fun updateCall() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = ApiClient.apiService.getProduct()
            if (response.isSuccessful && response.body() != null) {
                val content = response.body()
                content?.let { populateDatabase(productDao, it) }
            }
        } catch (e: Exception) {
            e.message?.let { e.printStackTrace() }
        }
    }

    private fun populateDatabase(productDao: ProductDao, productList: List<Product>) {
        productDao.deleteAllProducts()
        productList.forEach { product ->
            productDao.insert(product)
        }
    }
}