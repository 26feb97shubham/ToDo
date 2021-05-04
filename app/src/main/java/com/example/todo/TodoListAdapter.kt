package com.example.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.TodoItemBinding

class TodoListAdapter(val todoEvents : TodoEvents) :
    RecyclerView.Adapter<TodoListAdapter.ViewHolder>(), Filterable {
    private var todoList: List<TodoRecord> = arrayListOf()
    private var filteredTodoList: List<TodoRecord> = arrayListOf()
    private val listener: TodoEvents = todoEvents
    inner class ViewHolder(private val binding : TodoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: TodoRecord, listener: TodoEvents) {
            binding.tvItemTitle.text = todo.title
            binding.tvItemContent.text = todo.content

            binding.ivItemDelete.setOnClickListener {
                listener.onDeleteClicked(todo)
            }

            binding.item.setOnClickListener {
                listener.onViewClicked(todo)
            }
        }
    }
    interface TodoEvents {
        fun onDeleteClicked(todoRecord: TodoRecord)
        fun onViewClicked(todoRecord: TodoRecord)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TodoItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredTodoList[position], listener)
    }

    override fun getItemCount(): Int {
        return filteredTodoList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charString = p0.toString()
                filteredTodoList = if (charString.isEmpty()) {
                    todoList
                } else {
                    val filteredList = arrayListOf<TodoRecord>()
                    for (row in todoList) {
                        if (row.title.toLowerCase().contains(charString.toLowerCase())
                            || row.content.contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = filteredTodoList
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredTodoList = p1?.values as List<TodoRecord>
                notifyDataSetChanged()
            }
        }
    }

    fun setAllTodoItems(todoItems: List<TodoRecord>) {
        this.todoList = todoItems
        this.filteredTodoList = todoItems
        notifyDataSetChanged()
    }
}