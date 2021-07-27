package com.beaudoul.mcommerce.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels

import com.beaudoul.mcommerce.R
import com.beaudoul.mcommerce.api.ApiClient
import com.beaudoul.mcommerce.application.App

import com.beaudoul.mcommerce.model.Product
import com.beaudoul.mcommerce.viewmodel.ProductViewModel
import com.beaudoul.mcommerce.viewmodel.ProductViewModelFactory
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity(){

    private val productViewModel: ProductViewModel by viewModels {
        ProductViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        GlobalScope.launch (Dispatchers.IO) {
            withContext (Dispatchers.Main) {
                productViewModel.updateDatabase()
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }, 1000)
            }
        }
    }
}