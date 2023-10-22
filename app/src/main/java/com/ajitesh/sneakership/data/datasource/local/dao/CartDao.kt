package com.ajitesh.sneakership.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ajitesh.sneakership.data.datasource.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart")
    fun getAll(): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg cartEntities: CartEntity)

    @Delete
    fun delete(cartEntity: CartEntity)

    @Query("DELETE FROM cart")
    suspend fun deleteAll()
}