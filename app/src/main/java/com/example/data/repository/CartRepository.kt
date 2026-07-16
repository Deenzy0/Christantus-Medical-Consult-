package com.example.data.repository

import com.example.data.db.CartDao
import com.example.data.db.CartItem
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {
    val allCartItems: Flow<List<CartItem>> = cartDao.getAllCartItems()

    suspend fun insert(cartItem: CartItem) = cartDao.insertCartItem(cartItem)

    suspend fun deleteById(id: Int) = cartDao.deleteCartItemById(id)

    suspend fun clearCart() = cartDao.clearCart()
}
