package com.beaudoul.mcommerce.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product(@PrimaryKey(autoGenerate = false) val id: Int,
                   val name: String,
                   val price: Double,
                   val image: String)