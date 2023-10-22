package com.ajitesh.sneakership.data.di

import android.app.Application
import androidx.room.Room
import com.ajitesh.sneakership.data.datasource.local.AppDatabase
import com.ajitesh.sneakership.data.datasource.local.dao.CartDao
import com.ajitesh.sneakership.data.datasource.local.dao.SneakerDao
import com.ajitesh.sneakership.data.repository.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java,
            "sneaker-database"
        ).build()

    @Provides
    fun provideSneakerDao(appDatabase: AppDatabase): SneakerDao{
        return appDatabase.sneakerDao()
    }

    @Provides
    fun provideCartDao(appDatabase: AppDatabase): CartDao{
        return appDatabase.cartDao()
    }
}