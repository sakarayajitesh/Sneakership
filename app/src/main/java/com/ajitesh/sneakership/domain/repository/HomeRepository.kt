package com.ajitesh.sneakership.domain.repository

import com.ajitesh.sneakership.domain.data.Sneaker
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getAllSneakers(): Flow<List<Sneaker>>
    suspend fun fetchData()
    suspend fun addToCart(id: String)
}