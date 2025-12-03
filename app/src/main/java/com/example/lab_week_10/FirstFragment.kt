package com.example.lab_week_10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lab_week_10.database.TotalObject

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

    private fun updateText(totalObj: TotalObject, view: View) {
        view.findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, totalObj.value)
    }

    private fun prepareViewModel(view: View) {
        val viewModel = ViewModelProvider(requireActivity())[TotalViewModel::class.java]

        viewModel.total.observe(viewLifecycleOwner) { totalObj ->
            updateText(totalObj, view)
        }
    }

    companion object {
        fun newInstance() = FirstFragment()
    }
}
