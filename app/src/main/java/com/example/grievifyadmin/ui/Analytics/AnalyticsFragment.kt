package com.example.grievifyadmin.ui.Analytics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grievifyadmin.adapters.EmployeeListAdapter
import com.example.grievifyadmin.adapters.InProgressAdapter
import com.example.grievifyadmin.dataClass.TicketData
import com.example.grievifyadmin.dataClass.UserModel
import com.example.grievifyadmin.databinding.FragmentAnalyticsBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel


class AnalyticsFragment : Fragment() {
    lateinit var tvR:TextView
    lateinit var tvPython:TextView
    lateinit var tvCPP:TextView
    lateinit var tvJava:TextView
    lateinit var pieChart:PieChart
    private var _binding: FragmentAnalyticsBinding? = null
    private val ticketProgressArrayList=ArrayList<TicketData>()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var category=""
    private val adminArrayList=ArrayList<UserModel>()
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
        val databaseItem =
            FirebaseDatabase.getInstance()
                .getReference("Admin")

        databaseItem.get().addOnSuccessListener { snapshot ->

            val user = snapshot.child(Firebase.auth.currentUser?.uid.toString()).getValue(
                UserModel::class.java)
            if (user != null) {
                category=user.department.toString()
                fetchData(category)
                var textCategory: String
                if(category=="Acad")
                {
                    textCategory=" Academic Section\n Reports"
                }
                else if(category=="SW")
                {
                    textCategory=" Student Welfare Section\n Reports"
                }
                else
                {
                    textCategory=" Administrative Section\n Reports"
                }

                binding.category.text=textCategory
            }
            for(admin in snapshot.children)
            {
                val thisUser=admin.getValue(UserModel::class.java)
                    if(category== thisUser?.department)
                    {
                        adminArrayList.add(thisUser)
                    }


            }
            setList()
        }

        return binding.root
    }

    private fun setList() {
        println(adminArrayList)
        binding.employeeList.adapter =
            context?.let { it1 ->EmployeeListAdapter(it1,adminArrayList ) }
        binding.employeeList.layoutManager = LinearLayoutManager(context)
    }

    private fun fetchData(category: String) {
        val database = FirebaseDatabase.getInstance()
        val myReference: DatabaseReference = database.reference.child("tickets")
        myReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                ticketProgressArrayList.clear()
                if (snapshot.exists()) {
                    var progress=0
                    var resolved=0
                    var assigned=0
                    for (cartItemIDs in snapshot.children) {

                        println(cartItemIDs.value.toString())
                        val item = cartItemIDs.getValue(TicketData::class.java)

                        if (item != null) {
                            if(item.status=="InProgress" && item.category==category)
                            {
                                progress++
                            }
                            if(item.status=="Resolved" && item.category==category)
                            {
                                resolved++
                            }
                            if(item.status=="Assigned" && item.category==category)
                            {
                                assigned++
                            }

                        }
                    }
                    val total=resolved + progress + assigned
                    tvR.text = resolved.toString()
                    tvPython.text = progress.toString()
                    tvCPP.text = assigned.toString()
                    tvJava.text = total.toString()
                    setData()
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        )
    }

    private fun setData() {




        // Set the data and color to the pie chart
        pieChart.addPieSlice(
            PieModel(
                "Resolved", tvR.text.toString().toInt().toFloat(),
                Color.parseColor("#4CAF50")
            )
        )
        pieChart.addPieSlice(
            PieModel(
                "Progress", tvPython.text.toString().toInt().toFloat(),
                Color.parseColor("#FFEB3B")
            )
        )
        pieChart.addPieSlice(
            PieModel(
                "Assigned", tvCPP.text.toString().toInt().toFloat(),
                Color.parseColor("#F44336")
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