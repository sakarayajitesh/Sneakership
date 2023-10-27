package com.ajitesh.sneakership.data.repository

import com.ajitesh.sneakership.data.FakeData
import com.ajitesh.sneakership.data.datasource.local.dao.CartDao
import com.ajitesh.sneakership.data.datasource.local.dao.SneakerDao
import com.ajitesh.sneakership.data.datasource.local.entity.CartEntity
import com.ajitesh.sneakership.domain.repository.HomeRepository
import com.ajitesh.sneakership.toSneakerEntityList
import com.ajitesh.sneakership.toSneakerList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


class HomeRepositoryImpl(
    private val sneakerDao: SneakerDao,
    private val cartDao: CartDao,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : HomeRepository {

    override suspend fun fetchData() {
        withContext(defaultDispatcher) {
            cartDao.deleteAll()
            sneakerDao.deleteAll()
            val sneakerEntityList = FakeData.getFakeSneakerList().toSneakerEntityList()
            sneakerDao.insertAll(*sneakerEntityList.toTypedArray())
        }
    }

    override fun getAllSneakers() = sneakerDao.getAll().map {
        it.toSneakerList()
    }

    override suspend fun addToCart(id: String) {
        val cart = CartEntity(id)
        withContext(defaultDispatcher) {
            cartDao.insertAll(cart)
        }

    }
}