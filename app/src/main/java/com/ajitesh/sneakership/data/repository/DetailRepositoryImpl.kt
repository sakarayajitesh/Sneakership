package com.ajitesh.sneakership.data.repository

import com.ajitesh.sneakership.data.datasource.local.dao.CartDao
import com.ajitesh.sneakership.data.datasource.local.dao.SneakerDao
import com.ajitesh.sneakership.data.datasource.local.entity.CartEntity
import com.ajitesh.sneakership.domain.repository.DetailRepository
import com.ajitesh.sneakership.toSneaker

class DetailRepositoryImpl(private val sneakerDao: SneakerDao, private val cartDao: CartDao) :
    DetailRepository {

    override suspend fun getSneakerById(id: String) = sneakerDao.getById(id).toSneaker()

    override suspend fun addToCart(id: String) {
        val cart = CartEntity(id)
        cartDao.insertAll(cart)
    }
}