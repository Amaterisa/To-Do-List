package com.example.todolist.todolist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.database.ToDoListDatabase
import com.example.todolist.database.getDatabase
import com.example.todolist.databinding.ToDoListFragmentBinding

class ToDoListFragment : Fragment() {

    private lateinit var viewModel: ToDoListViewModel

    private var viewModelAdapter: ToDoListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = ToDoListFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this

        viewModelAdapter = ToDoListAdapter( { position, checked: Boolean -> onListItemClick(position, checked) }) { position -> onMenuItemClicked(position)}

        val application = requireNotNull(this.activity).application

        val viewModelFactory = ToDoListViewModelFactory(application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ToDoListViewModel::class.java)

        binding.viewModel = viewModel

        binding.addButton.setOnClickListener { v ->
            v.findNavController().navigate(ToDoListFragmentDirections.actionToDoListFragmentToCreateItemFragment())
        }

        viewModel.toDos.observe(viewLifecycleOwner, { newList ->
            val size = viewModelAdapter?.toDos?.size
            if (size != newList.size || newList.isEmpty()){
                viewModelAdapter?.toDos = newList
            }
            hideLoading(binding)
            binding.noItemsText.visibility = if (newList.isEmpty()) View.VISIBLE else View.GONE
        })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        showLoading(binding)

        registerForContextMenu(binding.recyclerView)

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun onListItemClick(position: Int, checked: Boolean) {
        viewModelAdapter?.toDos?.get(position)?.let { viewModel.markDone(it.createdDate, checked) }
    }

    private fun onMenuItemClicked(position: Int) {
        viewModelAdapter?.toDos?.get(position)?.let { viewModel.delete(it.createdDate) }
    }

    private fun hideLoading(binding: ToDoListFragmentBinding){
        binding.recyclerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoading(binding: ToDoListFragmentBinding){
        binding.recyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        binding.noItemsText.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.clear_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.clear -> viewModel.clear()
            }
        return true
    }
}