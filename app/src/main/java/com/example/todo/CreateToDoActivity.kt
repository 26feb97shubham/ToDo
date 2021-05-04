package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.todo.databinding.ActivityCreateToDoBinding

class CreateToDoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCreateToDoBinding
    private  var todoRecord: TodoRecord? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create_to_do)

        val intent = intent
        if (intent != null && intent.hasExtra(Constants.INTENT_OBJECT)) {
            val todoRecord: TodoRecord = intent.getParcelableExtra(Constants.INTENT_OBJECT)!!
            this.todoRecord = todoRecord
            prePopulateData(todoRecord)
        }

        title = if (todoRecord != null) getString(R.string.viewOrEditTodo) else getString(R.string.createTodo)
    }

    private fun prePopulateData(todoRecord: TodoRecord) {
        binding.etTodoTitle.setText(todoRecord.title)
        binding.etTodoContent.setText(todoRecord.content)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflate = menuInflater
        menuInflate.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.save_todo -> {
                saveTodo()
            }
        }
        return true
    }

    private fun saveTodo() {
        if (validateFields()) {
            val id = if (todoRecord != null) todoRecord?.id else null
            val todo = TodoRecord(id = id, title = binding.etTodoTitle.text.toString(), content = binding.etTodoContent.text.toString())
            val intent = Intent()
            intent.putExtra(Constants.INTENT_OBJECT, todo)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun validateFields(): Boolean {
        if (binding.etTodoTitle.text.isEmpty()) {
            binding.etTodoTitle.error = getString(R.string.pleaseEnterTitle)
            binding.etTodoTitle.requestFocus()
            return false
        }
        if (binding.etTodoContent.text.isEmpty()) {
            binding.etTodoContent.error = getString(R.string.pleaseEnterContent)
            binding.etTodoContent.requestFocus()
            return false
        }
        return true
    }
}