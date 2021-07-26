package com.beaudoul.mcommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.beaudoul.mcommerce.api.ApiClient
import com.beaudoul.mcommerce.model.Product
import kotlinx.coroutines.*
import retrofit2.Response

class SplashActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        GlobalScope.launch (Dispatchers.IO) {
            withContext (Dispatchers.Main) {
                Thread.sleep(1000)
                updateCall()
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    suspend fun updateCall() = coroutineScope {
        launch {
            try {
                val response = ApiClient.apiService.getProduct()
                if (response.isSuccessful && response.body() != null) {
                    val content = response.body()
                    content?.let { saveInDatabase(it) }
                    showMessage(response.message())
                } else {
                    showMessage(response.message())
                }

            } catch (e: Exception) {
                e.message?.let { showMessage(it) }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            this@SplashActivity,
            message,
            Toast.LENGTH_LONG
        ).show()
    }

    fun saveInDatabase(content: MutableList<Product>) {
        TODO()
    }
}