package com.example.dothisthing.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.dothisthing.databinding.FragmentAddToDoPopUpBinding
import com.example.dothisthing.utils.model.ToDoData
import com.google.android.material.textfield.TextInputEditText


class AddToDoPopUpFragment : DialogFragment(){

    private lateinit var binding: FragmentAddToDoPopUpBinding
    private var listener: DialogNextBtnClickListener? = null
    private var toDoData : ToDoData? = null

    fun setListener(listener: DialogNextBtnClickListener){
        this.listener = listener
    }

    companion object {
        const val TAG = "AddTodoPopupFragment"

        @JvmStatic
        fun newInstance(taskId:String, task:String) = AddToDoPopUpFragment().apply {
            arguments = Bundle().apply {
                putString("taskId",taskId)
                putString("task", task)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddToDoPopUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            toDoData = ToDoData(
                arguments?.getString("taskId").toString(),
                arguments?.getString("task").toString()
            )
            binding.todoEt.setText(toDoData?.task)
        }

        registerEvents()
    }

    private fun registerEvents() {
        binding.todoNextBtn.setOnClickListener {
            val todoTask = binding.todoEt.text.toString()
            if (todoTask.isNotEmpty()) {
                if (toDoData == null) {
                    listener?.onSaveTasks(todoTask, binding.todoEt)
                } else {
                    toDoData?.task = todoTask
                    listener?.onUpdateTasks(toDoData!!, binding.todoEt)
                }
                dismiss()
            } else {
                Toast.makeText(context, "Please type some tasks!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.todoClose.setOnClickListener {
            dismiss()
        }
    }

    interface DialogNextBtnClickListener {
        fun onSaveTasks(todo: String, todoEt:TextInputEditText)
        fun onUpdateTasks(toDoData: ToDoData, todoEt:TextInputEditText)
    }


}