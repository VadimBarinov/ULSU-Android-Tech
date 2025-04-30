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

    @Query("SELECT 'id' FROM Bikes LIMIT 1")
    fun getFirstId(): Flow<Int>

}