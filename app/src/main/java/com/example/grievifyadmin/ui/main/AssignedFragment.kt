package com.example.grievifyadmin.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grievifyadmin.R
import com.example.grievifyadmin.adapters.AssignedAdapter
import com.example.grievifyadmin.dataClass.TicketData
import com.example.grievifyadmin.databinding.FragmentAssignedBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class AssignedFragment : Fragment() {

    lateinit var binding: FragmentAssignedBinding
    private val ticketAssignedArrayList=ArrayList<TicketData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAssignedBinding.inflate(layoutInflater)
        fetchData()
        return binding.root
    }

    private fun fetchData() {
        binding.idRVAssigned.adapter= context?.let {it1-> AssignedAdapter(it1,ticketAssignedArrayList) }
        binding.idRVAssigned.layoutManager= LinearLayoutManager(context)
        val user = Firebase.auth.currentUser
        val database= FirebaseDatabase.getInstance()
        val myReference: DatabaseReference =database.reference.child("tickets")
        myReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.animationView.visibility=View.GONE
                ticketAssignedArrayList.clear()
                if(snapshot.exists())
                {
                    for (cartItemIDs in snapshot.children) {

                        println(cartItemIDs.value.toString())
                        val item=cartItemIDs.getValue(TicketData::class.java)
                        if(item!=null)
                        {
                            if(item.status=="Assigned")
                            {
                                ticketAssignedArrayList.add(item)
                            }
                        }
                        binding.idRVAssigned.adapter?.notifyDataSetChanged()
                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {

            }

        }
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AssignedFragment().apply {
                arguments = Bundle().apply {
                    putString("ARG_PARAM1", param1)
                    putString("ARG_PARAM2", param2)
                }
            }
    }
}