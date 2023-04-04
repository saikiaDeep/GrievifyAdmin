package com.example.grievifyadmin.ui.Analytics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.grievifyadmin.databinding.FragmentAnalyticsBinding
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel


class AnalyticsFragment : Fragment() {
    lateinit var tvR:TextView
    lateinit var tvPython:TextView
    lateinit var tvCPP:TextView
    lateinit var tvJava:TextView
    lateinit var pieChart:PieChart
    private var _binding: FragmentAnalyticsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        tvR=binding.tvR
        tvPython=binding.tvPython
        tvCPP=binding.tvCPP
        tvJava=binding.tvJava
        pieChart=binding.piechart
        setData()
        return binding.root
    }
    private fun setData() {

        // Set the percentage of language used
        tvR.setText(Integer.toString(40))
        tvPython.setText(Integer.toString(30))
        tvCPP.setText(Integer.toString(5))
        tvJava.setText(Integer.toString(25))

        // Set the data and color to the pie chart
        pieChart.addPieSlice(
            PieModel(
                "R", tvR.getText().toString().toInt().toFloat(),
                Color.parseColor("#FFA726")
            )
        )
        pieChart.addPieSlice(
            PieModel(
                "Python", tvPython.getText().toString().toInt().toFloat(),
                Color.parseColor("#66BB6A")
            )
        )
        pieChart.addPieSlice(
            PieModel(
                "C++", tvCPP.getText().toString().toInt().toFloat(),
                Color.parseColor("#EF5350")
            )
        )
        pieChart.addPieSlice(
            PieModel(
                "Java", tvJava.getText().toString().toInt().toFloat(),
                Color.parseColor("#29B6F6")
            )
        )

        // To animate the pie chart
        pieChart.startAnimation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}