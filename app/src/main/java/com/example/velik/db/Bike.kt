package com.example.velik.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Bikes")
data class Bike (

    @PrimaryKey (autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo (name = "name")
    var name: String,

    @ColumnInfo (name = "image")
    var image: String,

    @ColumnInfo (name = "description")
    var description: String,

    @ColumnInfo (name = "brand")
    var brand: String,

    @ColumnInfo (name = "season")
    var season: Int,

    @ColumnInfo (name = "sex")
    var sex: String,

    @ColumnInfo (name = "age")
    var age: String,

    @ColumnInfo (name = "type")
    var type: String,

    @ColumnInfo (name = "purpose")
    var purpose: String,

    @ColumnInfo (name = "fastening")
    var fastening: String,

    @ColumnInfo (name = "level")
    var level: String,

    @ColumnInfo (name = "material")
    var material: String,

    @ColumnInfo (name = "speeds")
    var speeds: Int,

    @ColumnInfo (name = "favorite")
    var favorite: Boolean,

)