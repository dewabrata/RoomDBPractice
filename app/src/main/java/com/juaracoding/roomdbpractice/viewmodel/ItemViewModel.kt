package com.juaracoding.roomdbpractice.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.juaracoding.roomdbpractice.dao.ItemDatabase
import com.juaracoding.roomdbpractice.model.ItemModel
import kotlinx.coroutines.launch

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val itemDao = ItemDatabase.getDatabase(application).item()
    val allItem:LiveData<List<ItemModel>> = itemDao.getAll()

    fun insert(item : ItemModel) = viewModelScope.launch {
        itemDao.insert(item)

    }

    fun delete(itemId: Int) = viewModelScope.launch {
        itemDao.deleteById(itemId)

    }






}