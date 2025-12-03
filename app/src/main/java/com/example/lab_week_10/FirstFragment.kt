package com.example.lab_week_10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModel(view)
    }

    private fun updateText(total: Int, view: View) {
        view.findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, total)
    }

    private fun prepareViewModel(view: View) {
        val viewModel =
            ViewModelProvider(requireActivity())[TotalViewModel::class.java]

        viewModel.total.observe(viewLifecycleOwner) { total ->
            updateText(total, view)
        }
    }

    companion object {
        fun newInstance() = FirstFragment()
    }
}
