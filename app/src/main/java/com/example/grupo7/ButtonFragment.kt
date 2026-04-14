package com.example.grupo7

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText

data class Task(val id: Int, val name: String, var completed: Boolean = false)

class TaskAdapter(
    private var tasks: List<Task>,
    private val onCheck: (Task) -> Unit,
    private val onDelete: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cb  = view.findViewById<CheckBox>(R.id.cb_task)
        val tv  = view.findViewById<TextView>(R.id.tv_task_name)
        val btn = view.findViewById<MaterialButton>(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(v)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.tv.text = task.name
        holder.cb.isChecked = task.completed

        if (task.completed) {
            holder.tv.paintFlags = holder.tv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.tv.alpha = 0.5f
        } else {
            holder.tv.paintFlags = holder.tv.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.tv.alpha = 1.0f
        }

        holder.cb.setOnClickListener { onCheck(task) }
        holder.btn.setOnClickListener { onDelete(task) }
    }

    override fun getItemCount() = tasks.size

    fun updateList(newList: List<Task>) {
        tasks = newList
        notifyDataSetChanged()
    }
}

class ButtonFragment : Fragment() {

    private val allTasks = mutableListOf<Task>()
    private var nextId = 1
    private var currentFilter = 0
    private lateinit var adapter: TaskAdapter
    private lateinit var tvCount: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_button, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvCount = view.findViewById(R.id.tv_task_count)
        val etTask   = view.findViewById<TextInputEditText>(R.id.et_task)
        val btnAdd   = view.findViewById<MaterialButton>(R.id.btn_add)
        val rv       = view.findViewById<RecyclerView>(R.id.rv_tasks)
        val tabs     = view.findViewById<TabLayout>(R.id.tab_filter)
        val btnClear = view.findViewById<MaterialButton>(R.id.btn_clear_completed)

        adapter = TaskAdapter(emptyList(),
            onCheck  = { task -> toggleTask(task) },
            onDelete = { task -> deleteTask(task) }
        )
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter

        btnAdd.setOnClickListener { addTask(etTask) }

        etTask.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) { addTask(etTask); true } else false
        }

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) { currentFilter = tab.position; refreshList() }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        btnClear.setOnClickListener {
            val removed = allTasks.count { it.completed }
            allTasks.removeAll { it.completed }
            refreshList()
            Toast.makeText(context, "$removed tarea(s) eliminada(s)", Toast.LENGTH_SHORT).show()
        }

        refreshList()
    }

    private fun addTask(et: TextInputEditText) {
        val text = et.text.toString().trim()
        if (text.isEmpty()) {
            Toast.makeText(context, "Escribe una tarea primero", Toast.LENGTH_SHORT).show()
            return
        }
        allTasks.add(Task(nextId++, text))
        et.text?.clear()
        refreshList()
    }

    private fun toggleTask(task: Task) {
        val index = allTasks.indexOfFirst { it.id == task.id }
        if (index != -1) { allTasks[index].completed = !allTasks[index].completed; refreshList() }
    }

    private fun deleteTask(task: Task) {
        allTasks.removeAll { it.id == task.id }
        refreshList()
        Toast.makeText(context, "Tarea eliminada", Toast.LENGTH_SHORT).show()
    }

    private fun refreshList() {
        val filtered = when (currentFilter) {
            1    -> allTasks.filter { !it.completed }
            2    -> allTasks.filter { it.completed }
            else -> allTasks.toList()
        }
        adapter.updateList(filtered)
        val pending = allTasks.count { !it.completed }
        tvCount.text = "$pending tarea(s) pendiente(s)"
    }
}
