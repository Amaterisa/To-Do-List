package com.example.todolist.todolist

import android.view.*
import android.widget.CheckBox
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.database.ToDoItem
import com.example.todolist.databinding.ToDoItemBinding

class ToDoListAdapter(private val onItemClicked: (position: Int, checked: Boolean) -> Unit,
                      private val onMenuItemClicked: (position: Int) -> Unit
                      ): RecyclerView.Adapter<ToDoListViewHolder>() {
    var toDos: List<ToDoItem> = emptyList()
    set (value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
        val withDataBinding: ToDoItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ToDoListViewHolder.LAYOUT,
            parent,
            false)

        return ToDoListViewHolder(withDataBinding, onItemClicked, onMenuItemClicked)
    }

    override fun getItemCount() = toDos.size

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        holder.viewDataBinding.toDo = toDos[position]
    }
}

class ToDoListViewHolder(
    val viewDataBinding: ToDoItemBinding,
    private val onItemClicked: (position: Int, checked: Boolean) -> Unit,
    private val onMenuItemClicked: (position: Int) -> Unit,
) : RecyclerView.ViewHolder(viewDataBinding.root), View.OnClickListener,
    View.OnCreateContextMenuListener{
    companion object{
        @LayoutRes
        val LAYOUT = R.layout.to_do_item
    }

    init {
        viewDataBinding.checkbox.setOnClickListener{ v ->
            onClick(v)
        }

        viewDataBinding.checkbox.setOnCreateContextMenuListener { menu, v, menuInfo ->
            onCreateContextMenu(menu, v, menuInfo)
        }
    }

    override fun onClick(v: View) {
        val position = adapterPosition
        if (v is CheckBox){
            val checked: Boolean = v.isChecked
            onItemClicked(position, checked)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        view: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val delete = menu?.add(Menu.NONE, 1, 1, "Delete")
        delete?.setOnMenuItemClickListener(onClick)
    }

    private val onClick: MenuItem.OnMenuItemClickListener = object :
        MenuItem.OnMenuItemClickListener {
        override fun onMenuItemClick(item: MenuItem): Boolean {
            when (item.itemId) {
                1 -> {
                    val position = adapterPosition
                    onMenuItemClicked(position)
                    return true
                }
            }
            return false
        }
    }
}
