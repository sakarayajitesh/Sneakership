package com.ajitesh.sneakership.data.repository

import com.ajitesh.sneakership.data.datasource.local.dao.CartDao
import com.ajitesh.sneakership.data.datasource.local.dao.SneakerDao
import com.ajitesh.sneakership.data.datasource.local.entity.CartEntity
import com.ajitesh.sneakership.domain.data.Sneaker
import com.ajitesh.sneakership.domain.repository.CartRepository
import com.ajitesh.sneakership.toSneakerList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CartRepositoryImpl(private val sneakerDao: SneakerDao, private val cartDao: CartDao) :
    CartRepository {

    override fun getAll() = cartDao.getAll().map {
        val sneakerListIds = it.map { it.sneakerId }
        sneakerDao.getByIds(sneakerListIds).toSneakerList()
    }

    override suspend fun delete(id: String) {
        val cartEntity = CartEntity(id)
        withContext(Dispatchers.IO){
            cartDao.delete(cartEntity)
        }
    }
}