package com.ajitesh.sneakership.data.repository

import com.ajitesh.sneakership.data.datasource.local.dao.CartDao
import com.ajitesh.sneakership.data.datasource.local.dao.SneakerDao
import com.ajitesh.sneakership.data.datasource.local.entity.CartEntity
import com.ajitesh.sneakership.domain.repository.DetailRepository
import com.ajitesh.sneakership.toSneaker
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailRepositoryImpl(
    private val sneakerDao: SneakerDao,
    private val cartDao: CartDao,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DetailRepository {

    override suspend fun getSneakerById(id: String) = withContext(defaultDispatcher){
        sneakerDao.getById(id).toSneaker()
    }

    override suspend fun addToCart(id: String) {
        val cart = CartEntity(id)
        withContext(defaultDispatcher){
            cartDao.insertAll(cart)
        }
    }
}