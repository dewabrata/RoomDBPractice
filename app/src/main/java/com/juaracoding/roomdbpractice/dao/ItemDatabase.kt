package com.juaracoding.roomdbpractice.dao

import android.content.ClipData.Item
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.juaracoding.roomdbpractice.model.ItemModel

@Database(entities = [ItemModel::class], version = 1, exportSchema = false)
abstract class ItemDatabase :RoomDatabase() {

    abstract fun item() : ItemDao

    companion object {
        @Volatile
        private var INSTANCE : ItemDatabase?=null

        fun getDatabase(context: Context) : ItemDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDatabase::class.java,
                    "item_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }


    }


}