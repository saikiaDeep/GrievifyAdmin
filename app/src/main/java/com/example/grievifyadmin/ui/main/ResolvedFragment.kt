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
import com.example.grievifyadmin.adapters.ResolvedAdapter
import com.example.grievifyadmin.dataClass.TicketData
import com.example.grievifyadmin.dataClass.UserModel
import com.example.grievifyadmin.databinding.FragmentAssignedBinding
import com.example.grievifyadmin.databinding.FragmentResolvedBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResolvedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResolvedFragment : Fragment() {
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
    private lateinit var binding: FragmentResolvedBinding
    private val ticketResolvedArrayList=ArrayList<TicketData>()
    private var category:String=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResolvedBinding.inflate(layoutInflater)
        val databaseItem =
            FirebaseDatabase.getInstance()
                .getReference("Admin")

        databaseItem.get().addOnSuccessListener { snapshot ->
            val user = snapshot.child(Firebase.auth.currentUser?.uid.toString()).getValue(
                UserModel::class.java)
            if (user != null) {
                category=user.department.toString()
                fetchData()
            }
        }

        return binding.root
    }
    private fun fetchData() {
        binding.idRVResolved.adapter =
            context?.let { it1 -> ResolvedAdapter(it1, ticketResolvedArrayList) }
        binding.idRVResolved.layoutManager = LinearLayoutManager(context)
        val user = Firebase.auth.currentUser
        val database = FirebaseDatabase.getInstance()
        val myReference: DatabaseReference = database.reference.child("tickets")
        myReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.animationView.visibility=View.GONE
                ticketResolvedArrayList.clear()
                if (snapshot.exists()) {
                    for (cartItemIDs in snapshot.children) {

                        println(cartItemIDs.value.toString())
                        val item = cartItemIDs.getValue(TicketData::class.java)
                        if (item != null) {
                            if(item.status=="Resolved" && item.category==category)
                            {
                                ticketResolvedArrayList.add(item)
                            }

                        }
                        binding.idRVResolved.adapter?.notifyDataSetChanged()
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
         * @return A new instance of fragment ResolvedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResolvedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}