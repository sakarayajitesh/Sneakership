package com.ajitesh.sneakership.data.di

import com.ajitesh.sneakership.data.datasource.local.dao.SneakerDao
import com.ajitesh.sneakership.data.datasource.local.dao.CartDao
import com.ajitesh.sneakership.data.repository.HomeRepositoryImpl
import com.ajitesh.sneakership.domain.repository.HomeRepository
import com.ajitesh.sneakership.data.repository.DetailRepositoryImpl
import com.ajitesh.sneakership.domain.repository.DetailRepository
import com.ajitesh.sneakership.data.repository.CartRepositoryImpl
import com.ajitesh.sneakership.domain.repository.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideHomeRepository(sneakerDao: SneakerDao, cartDao: CartDao): HomeRepository{
        return HomeRepositoryImpl(sneakerDao, cartDao)
    }

    @Provides
    fun provideDetailRepository(sneakerDao: SneakerDao, cartDao: CartDao): DetailRepository{
        return DetailRepositoryImpl(sneakerDao, cartDao)
    }

    @Provides
    fun provideCartRepository(sneakerDao: SneakerDao, cartDao: CartDao): CartRepository{
        return CartRepositoryImpl(sneakerDao, cartDao)
    }
}