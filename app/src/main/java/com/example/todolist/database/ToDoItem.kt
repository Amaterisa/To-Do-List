package com.example.todolist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat

@Entity(tableName = "to_do_list_table")
data class ToDoItem(
    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "description")
    val description: String = "",

    @PrimaryKey(autoGenerate = true)
    val createdDate: Long = 0L,

    @ColumnInfo(name = "done_status")
    var done: Boolean = false
) {
    fun getDate(): String{
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        return "Created date: " + simpleDateFormat.format(createdDate)
    }
}
