package com.ajitesh.sneakership.domain.repository

import com.ajitesh.sneakership.domain.data.Sneaker

interface DetailRepository {
    suspend fun getSneakerById(id: String): Sneaker
    suspend fun addToCart(id: String)
}