package com.example.grievifyadmin.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.grievifyadmin.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailsFragment : Fragment() {
    private lateinit var phonenum: EditText
    private lateinit var name: EditText
    private lateinit var department: EditText
    private lateinit var submit: Button
    private lateinit var dtb: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        phonenum = view.findViewById(R.id.editTextphone)
        name = view.findViewById(R.id.editTextname)
        department = view.findViewById(R.id.editTextid)
        dtb = FirebaseDatabase.getInstance().reference
        submit = view.findViewById(R.id.button)
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!

        submit.setOnClickListener {
            val phone = phonenum.text.toString().trim()
            val nametxt = name.text.toString().trim()
            val id = department.text.toString().trim()
            if (phone.isBlank() || nametxt.isBlank()) {
                phonenum.setError("Aestrik fields are required")
                name.setError("Aestrik fields are required")


            } else if (phone.length != 10)
                phonenum.setError("Number should be of 10 digits")
            else {
                dtb.child("Users").child(user.uid.toString()).get().addOnSuccessListener {

                    dtb.child("Users").child(user.uid.toString()).child("infoentered")
                        .setValue("yes")
                    dtb.child("Users").child(user.uid).child("phonenum").setValue(phone)
                    dtb.child("Users").child(user.uid).child("name").setValue(nametxt)
                    dtb.child("Users").child(user.uid).child("department").setValue(id)
                    fragmentload(LoginFragment())

                }.addOnFailureListener {

                }
            }

        }

        return view
    }
    private fun fragmentload(fragment : Fragment)
    {

        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.authFrameLayout, fragment)
        fragmentTransaction.commit()

    }


}