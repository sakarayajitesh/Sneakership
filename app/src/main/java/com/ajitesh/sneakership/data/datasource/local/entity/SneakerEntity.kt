package com.ajitesh.sneakership.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sneakers")
data class SneakerEntity(
    @PrimaryKey val id: String,
    val title: String,
    val price: Int,
    val image: String,
    val brand: String,
    @ColumnInfo("year_of_release")
    val yearOfRelease: String
)

