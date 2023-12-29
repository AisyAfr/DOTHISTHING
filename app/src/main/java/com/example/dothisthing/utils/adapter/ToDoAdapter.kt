package com.example.dothisthing.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dothisthing.databinding.TodoItemBinding
import com.example.dothisthing.utils.model.ToDoData

class ToDoAdapter(private val list:MutableList<ToDoData>):
RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>(){

    private var listener:ToDoAdapterClicksInterface? = null
    fun setListener(listener:ToDoAdapterClicksInterface){
        this.listener = listener
    }
    inner class ToDoViewHolder(val binding:TodoItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        return ToDoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        with(holder) {
            with(list[position]){
                binding.todoTask.text = this.task

                binding.deleteTask.setOnClickListener {
                    listener?.onDeleteItemClicked(this, position)
                }

                binding.editTask.setOnClickListener {
                    listener?.onEditItemClicked(this, position)
                }
            }
        }
    }

    interface ToDoAdapterClicksInterface{
        fun onDeleteItemClicked(toDoData: ToDoData , position : Int)
        fun onEditItemClicked(toDoData: ToDoData , position: Int)
    }
}