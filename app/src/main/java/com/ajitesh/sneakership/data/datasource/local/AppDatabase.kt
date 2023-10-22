package com.ajitesh.sneakership.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ajitesh.sneakership.data.datasource.local.dao.CartDao
import com.ajitesh.sneakership.data.datasource.local.dao.SneakerDao
import com.ajitesh.sneakership.data.datasource.local.entity.CartEntity
import com.ajitesh.sneakership.data.datasource.local.entity.SneakerEntity

@Database(entities = [SneakerEntity::class, CartEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun sneakerDao(): SneakerDao
    abstract fun cartDao(): CartDao
}