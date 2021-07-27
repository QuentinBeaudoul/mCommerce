package com.beaudoul.mcommerce.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.beaudoul.mcommerce.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert
    fun insert(product: Product)
    @Delete
    fun delete(product: Product)
    @Query("delete from product_table")
    fun deleteAllProducts()
    @Query("select * from product_table")
    fun getAllProducts(): Flow<List<Product>>
}