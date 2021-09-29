package com.example.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDoItem::class], version = 1)
abstract class ToDoListDatabase : RoomDatabase() {
    abstract val toDoListDatabaseDao: ToDoListDatabaseDao
}

private lateinit var INSTANCE: ToDoListDatabase

fun getDatabase(context: Context): ToDoListDatabase {
    synchronized(ToDoListDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ToDoListDatabase::class.java,
                "toDos")
                .build()
        }
    }
    return INSTANCE
}

