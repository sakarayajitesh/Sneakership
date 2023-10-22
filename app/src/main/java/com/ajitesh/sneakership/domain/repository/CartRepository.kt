package com.ajitesh.sneakership.domain.repository

import com.ajitesh.sneakership.domain.data.Sneaker
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getAll(): Flow<List<Sneaker>>
    suspend fun delete(id: String)
}