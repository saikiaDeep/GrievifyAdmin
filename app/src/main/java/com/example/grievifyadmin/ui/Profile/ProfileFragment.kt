package com.example.grievifyadmin.ui.Profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.grievifyadmin.AuthActivity
import com.example.grievifyadmin.StartActivity
import com.example.grievifyadmin.databinding.FragmentProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

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
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}