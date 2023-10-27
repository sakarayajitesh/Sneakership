package com.ajitesh.sneakership.data.repository

import com.ajitesh.sneakership.data.datasource.local.dao.CartDao
import com.ajitesh.sneakership.data.datasource.local.dao.SneakerDao
import com.ajitesh.sneakership.data.datasource.local.entity.CartEntity
import com.ajitesh.sneakership.domain.repository.CartRepository
import com.ajitesh.sneakership.toSneakerList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CartRepositoryImpl(
    private val sneakerDao: SneakerDao,
    private val cartDao: CartDao,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CartRepository {

    override fun getAll() = cartDao.getAll().map { cartList->
        val sneakerListIds = cartList.map { it.sneakerId }
        withContext(defaultDispatcher){
            sneakerDao.getByIds(sneakerListIds).toSneakerList()
        }
    }

    override suspend fun delete(id: String) {
        val cartEntity = CartEntity(id)
        withContext(defaultDispatcher) {
            cartDao.delete(cartEntity)
        }
    }
}