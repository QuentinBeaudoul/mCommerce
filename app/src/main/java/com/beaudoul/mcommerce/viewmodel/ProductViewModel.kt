package com.beaudoul.mcommerce.viewmodel

import androidx.lifecycle.*
import com.beaudoul.mcommerce.database.ProductRepository
import com.beaudoul.mcommerce.model.Product
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ProductViewModel(private val repository: ProductRepository): ViewModel() {
    val allProducts: LiveData<List<Product>> = repository.allProducts.asLiveData()

    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }

    fun updateDatabase() = viewModelScope.launch {
        repository.update()
    }
}
class ProductViewModelFactory(private val repository: ProductRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}