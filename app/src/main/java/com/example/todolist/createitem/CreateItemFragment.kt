package com.example.todolist.createitem

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.todolist.databinding.CreateItemFragmentBinding

class CreateItemFragment : Fragment() {

    private lateinit var viewModel: CreateItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CreateItemFragmentBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = CreateItemViewModelFactory(application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CreateItemViewModel::class.java)

        binding.lifecycleOwner = this

        binding.addButton.setOnClickListener { v ->
            val title = binding.titleTextInput.text.toString()
            val description = binding.descriptionTextInput.text.toString()
            if (title.isEmpty()){
                Toast.makeText(context, "Empty title!", Toast.LENGTH_SHORT).show()
            } else{
                viewModel.addToDo(title, description)
                v.findNavController().navigate(CreateItemFragmentDirections.actionCreateItemFragmentToToDoListFragment())
            }
        }

        binding.cancelButton.setOnClickListener { v ->
            v.findNavController().navigate(CreateItemFragmentDirections.actionCreateItemFragmentToToDoListFragment())
        }

        binding.titleTextInput.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                v.hideKeyboard()
            }
        }

        binding.descriptionTextInput.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                v.hideKeyboard()
            }
        }

        return binding.root
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}