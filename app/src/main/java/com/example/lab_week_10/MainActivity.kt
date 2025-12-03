package com.example.lab_week_10

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDatabase
import kotlinx.coroutines.launch

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
        viewModel.total.observe(this) { total ->
            textTotal.text = getString(R.string.text_total, total)
        }

        buttonIncrement.setOnClickListener {
            viewModel.incrementTotal()
        }
    }

    override fun onPause() {
        super.onPause()

        // Simpan total terakhir ke database
        val currentTotal = viewModel.total.value ?: 0
        lifecycleScope.launch {
            database.totalDao().update(Total(id = 1L, total = currentTotal))
        }
    }
}
