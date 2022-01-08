package com.example.todo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks= mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val onLongClickListener= object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                //1. Remove item from list
                listOfTasks.removeAt(position)
                //2. Notify adapter that data set changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }

        //1. detect when user clicks on 'add' button
  //      findViewById<Button>(R.id.button).setOnClickListener{
            // execute code when user clicks button



  //      }
        loadItems()

        //Look up recyclerview in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Create adapter passing in the sample user data
         adapter = TaskItemAdapter(listOfTasks,onLongClickListener)

        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter

        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up button and input field
        val inputTextField=findViewById<EditText>(R.id.addTask)
        //Set up button for user task input
        findViewById<Button>(R.id.button).setOnClickListener {
            //1. Grab text user imputted into @id/addTaskField
            val userInputtedTask=findViewById<EditText>(R.id.addTask).text.toString()
            //2.Add the string to out list of task: listOfTasks
            listOfTasks.add(userInputtedTask)

            //Notify the adapter data has been updated
            adapter.notifyItemInserted(listOfTasks.size-1)

            //3.Reset text field
            inputTextField.setText("")

            saveItems()

        }
    }

    //Save data user has entered
    //Save data by writing and reading from a file

    //Get file we need
    fun getDataFile() : File {

        //Every line will represent specific task
        return File(filesDir, "data.txt")
    }

    //Load items by reading every line in data file
    fun loadItems(){
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())

        }catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

    //Save items by writing these into our data file
    fun saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)

        }catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }
}