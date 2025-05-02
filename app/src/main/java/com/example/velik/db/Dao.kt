package com.example.velik.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert
    fun firstInsert(bikes: List<Bike>)

    @Query("SELECT * FROM Bikes")
    fun getAllBikes(): Flow<List<Bike>>

    @Query("SELECT * FROM Bikes WHERE favorite=TRUE")
    fun getFavoriteBikes(): Flow<List<Bike>>

    @Query("SELECT COUNT(*) FROM Bikes WHERE favorite=TRUE")
    fun getCountFavorite(): Int

    @Query("UPDATE Bikes SET favorite=:value WHERE id=:bikeId")
    fun changeFavorite(bikeId: Int?, value: Boolean)

    @Query("SELECT * FROM Bikes WHERE id=:bikeId LIMIT 1")
    fun getBikeById(bikeId: Int?): Flow<Bike>

}