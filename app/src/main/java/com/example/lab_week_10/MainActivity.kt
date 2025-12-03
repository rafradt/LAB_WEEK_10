package com.example.lab_week_10

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDatabase
import kotlinx.coroutines.launch
import java.util.*
import com.example.lab_week_10.database.TotalObject

class MainActivity : AppCompatActivity() {

    private val database by lazy { TotalDatabase.getInstance(this) }

    private val viewModel: TotalViewModel by viewModels {
        TotalViewModelFactory(database.totalDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textTotal = findViewById<TextView>(R.id.text_total)
        val buttonIncrement = findViewById<Button>(R.id.button_increment)

        // Observe LiveData
        viewModel.total.observe(this) { totalObj ->
            textTotal.text = getString(R.string.text_total, totalObj.value)
        }

        buttonIncrement.setOnClickListener {
            viewModel.incrementTotal()
        }
    }

    override fun onStart() {
        super.onStart()
        // Tampilkan total.date sebagai Toast
        val date = viewModel.total.value?.date
        if (!date.isNullOrEmpty()) {
            Toast.makeText(this, "Last updated: $date", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        // Update total.date ke current date saat pause
        val currentTotal = viewModel.total.value?.value ?: 0
        val newDate = Date().toString()
        lifecycleScope.launch {
            database.totalDao().update(Total(id = 1L, total = TotalObject(currentTotal, newDate)))
        }
    }
}
