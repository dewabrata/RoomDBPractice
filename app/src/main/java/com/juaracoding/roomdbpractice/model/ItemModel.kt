package com.juaracoding.roomdbpractice.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class ItemModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val nama :String,
    val foto : String
)