package com.ajitesh.sneakership.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ajitesh.sneakership.data.datasource.local.entity.SneakerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SneakerDao{

    @Query("SELECT * FROM sneakers")
    fun getAll(): Flow<List<SneakerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg sneakers: SneakerEntity)

    @Query("SELECT * FROM sneakers WHERE id IN (:id)")
    suspend fun getByIds(id: List<String>): List<SneakerEntity>

    @Query("SELECT * FROM sneakers WHERE id=:id")
    suspend fun getById(id: String): SneakerEntity

    @Query("DELETE FROM sneakers")
    suspend fun deleteAll()
}