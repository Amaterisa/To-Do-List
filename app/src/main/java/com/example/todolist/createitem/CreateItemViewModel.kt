package com.example.todolist.createitem

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.todolist.database.ToDoItem
import com.example.todolist.database.getDatabase
import com.example.todolist.todolist.ToDoListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateItemViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)

    fun addToDo(title: String, description: String = ""){
        val createdDate = System.currentTimeMillis()
        val newToDo = ToDoItem(title, description, createdDate)
        viewModelScope.launch {
            insert(newToDo)
        }
    }

    private suspend fun insert(item: ToDoItem) {
        withContext(Dispatchers.IO) {
            database.toDoListDatabaseDao.insert(item)
        }
    }
}

class CreateItemViewModelFactory(
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateItemViewModel::class.java)) {
            return CreateItemViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}