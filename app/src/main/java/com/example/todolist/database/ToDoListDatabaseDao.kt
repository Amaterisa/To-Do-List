package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ToDoListDatabaseDao {
    @Insert
    suspend fun insert(night: ToDoItem)

    @Update
    suspend fun update(night: ToDoItem)

    @Query("SELECT * from to_do_list_table WHERE createdDate = :key")
    suspend fun get(key: Long): ToDoItem?

    @Query("DELETE FROM to_do_list_table")
    suspend fun clear()

    @Query("SELECT * FROM to_do_list_table ORDER BY createdDate DESC")
    fun getToDoList(): LiveData<List<ToDoItem>>

    @Query("SELECT * from to_do_list_table WHERE createdDate = :key")
    fun getItemWithId(key: Long): LiveData<ToDoItem>

    @Query("DELETE from to_do_list_table WHERE createdDate = :key")
    suspend fun deleteItemWithId(key: Long)
}
