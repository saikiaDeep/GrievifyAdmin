package com.example.grievifyadmin.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grievifyadmin.R
import com.example.grievifyadmin.adapters.AssignedAdapter
import com.example.grievifyadmin.adapters.InProgressAdapter
import com.example.grievifyadmin.dataClass.TicketData
import com.example.grievifyadmin.databinding.FragmentAssignedBinding
import com.example.grievifyadmin.databinding.FragmentInProgressBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InProgressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InProgressFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private lateinit var binding: FragmentInProgressBinding
    private val ticketProgressArrayList=ArrayList<TicketData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInProgressBinding.inflate(layoutInflater)
            fetchData()
        return binding.root
    }

   

    private fun fetchData() {
        binding.idRVProgress.adapter =
            context?.let { it1 -> InProgressAdapter(it1, ticketProgressArrayList) }
        binding.idRVProgress.layoutManager = LinearLayoutManager(context)
        val user = Firebase.auth.currentUser
        val database = FirebaseDatabase.getInstance()
        val myReference: DatabaseReference = database.reference.child("tickets")
        myReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ticketProgressArrayList.clear()
                if (snapshot.exists()) {
                    for (cartItemIDs in snapshot.children) {

                        println(cartItemIDs.value.toString())
                        val item = cartItemIDs.getValue(TicketData::class.java)
                        if (item != null) {
                            if(item.status=="InProgress")
                            {
                                ticketProgressArrayList.add(item)
                            }

                        }
                        binding.idRVProgress.adapter?.notifyDataSetChanged()
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InProgressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InProgressFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}