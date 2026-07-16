package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.db.CartItem
import com.example.data.repository.CartRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CartRepository

    val cartItems: StateFlow<List<CartItem>>

    init {
        val cartDao = AppDatabase.getDatabase(application).cartDao()
        repository = CartRepository(cartDao)
        cartItems = repository.allCartItems.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun addToCart(cartItem: CartItem) {
        viewModelScope.launch {
            repository.insert(cartItem)
        }
    }

    fun removeFromCart(id: Int) {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}
