package com.beaudoul.mcommerce.application

import android.app.Application
import com.beaudoul.mcommerce.database.ProductDatabase
import com.beaudoul.mcommerce.database.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class App: Application() {
    private val database by lazy { ProductDatabase.getDatabase(this) }
    val repository by lazy { ProductRepository(database.productDao()) }
}