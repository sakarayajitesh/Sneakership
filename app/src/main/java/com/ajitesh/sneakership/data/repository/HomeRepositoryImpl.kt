package com.ajitesh.sneakership.data.repository

import com.ajitesh.sneakership.data.datasource.local.dao.CartDao
import com.ajitesh.sneakership.data.datasource.local.dao.SneakerDao
import com.ajitesh.sneakership.data.datasource.local.entity.CartEntity
import com.ajitesh.sneakership.domain.repository.HomeRepository
import com.ajitesh.sneakership.toSneakerEntityList
import com.ajitesh.sneakership.toSneakerList
import com.ajitesh.sneakership.data.FakeData
import kotlinx.coroutines.flow.map


class HomeRepositoryImpl(private val sneakerDao: SneakerDao, private val cartDao: CartDao): HomeRepository {

    override suspend fun fetchData() {
        cartDao.deleteAll()
        sneakerDao.deleteAll()
        val sneakerEntityList = FakeData.getFakeSneakerList().toSneakerEntityList()
        sneakerDao.insertAll(*sneakerEntityList.toTypedArray())
    }

    override fun getAllSneakers() = sneakerDao.getAll().map {
        it.toSneakerList()
    }

    override suspend fun addToCart(id: String) {
        val cart = CartEntity(id)
        cartDao.insertAll(cart)
    }
}