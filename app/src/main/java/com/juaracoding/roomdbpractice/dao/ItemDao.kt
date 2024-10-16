package com.juaracoding.roomdbpractice.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.juaracoding.roomdbpractice.model.ItemModel

@Dao
interface ItemDao {
    @Query("SELECT * FROM item order by id ASC")
    fun getAll(): List<ItemModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: ItemModel)

    @Query("DELETE FROM item")
    fun deleteAll()

    @Query("DELETE FROM item WHERE id = :itemId")
    fun deleteById(itemId: Int)

}