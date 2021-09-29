package com.example.todolist.todolist

import android.app.Application
import android.util.Log
import com.example.todolist.database.ToDoItem
import androidx.lifecycle.*
import com.example.todolist.database.getDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ToDoListViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application).toDoListDatabaseDao
    val toDos = database.getToDoList()

    fun delete(id: Long){
        viewModelScope.launch {
            database.deleteItemWithId(id)
        }
    }

    fun clear(){
        viewModelScope.launch {
            database.clear()
        }
    }

    fun markDone(id: Long, checked: Boolean){
        viewModelScope.launch {
            val item = database.get(id) ?: return@launch
            item.done = checked
            database.update(item)
        }
    }
}

class ToDoListViewModelFactory(
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoListViewModel::class.java)) {
            return ToDoListViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}