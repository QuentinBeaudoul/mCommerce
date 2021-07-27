package com.beaudoul.mcommerce.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.beaudoul.mcommerce.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    companion object {

        @Volatile
         private var instance: ProductDatabase? = null

        fun getDatabase(context: Context): ProductDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "product_database"
                ).build()
                instance = newInstance
                // return instance
                newInstance
            }
        }
    }
}
