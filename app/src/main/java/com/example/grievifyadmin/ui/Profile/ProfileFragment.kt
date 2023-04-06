package com.example.grievifyadmin.ui.Profile

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.grievifyadmin.AuthActivity
import com.example.grievifyadmin.StartActivity
import com.example.grievifyadmin.dataClass.UserModel
import com.example.grievifyadmin.databinding.FragmentProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myReference: DatabaseReference =database.reference.child("Admin")
    private var valueEventListener: ValueEventListener? = null

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.buttonEditProfile.setOnClickListener {
//            val i= Intent(context,StartActivity::class.java)
//            startActivity(i)
        }
        binding.buttonLogOut.setOnClickListener{
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle("Are you sure?")
            builder.setMessage("You will be logged out of your account")
            builder.setPositiveButton("Yes") { _, _ ->
                Firebase.auth.signOut()
                val intent = Intent(context, AuthActivity::class.java)
                startActivity(intent)
                activity?.finish()

            }
            builder.setNegativeButton("No") { _, _ ->
            }
            builder.show()

        }
        val databaseItem =
            FirebaseDatabase.getInstance()
                .getReference("Admin")

        databaseItem.get().addOnSuccessListener { snapshot ->
                val user = snapshot.child(Firebase.auth.currentUser?.uid.toString()).getValue(
                    UserModel::class.java)
                if (user != null) {
                    binding.EMAIL.text = user.email
                    binding.USERNAME.text = user.name
                    binding.dept.text = user.department
                    binding.PHONE.text = user.phonenum

                }



        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}