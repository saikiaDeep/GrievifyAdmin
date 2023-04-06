package com.example.grievifyadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.example.grievifyadmin.databinding.ActivityAuthBinding
import com.example.grievifyadmin.ui.auth.LoginFragment
import com.google.firebase.auth.FirebaseAuth

lateinit var binding : ActivityAuthBinding
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user=FirebaseAuth.getInstance().currentUser
        if(user!=null)
        {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
            finish()
        }
        else
        {
            fragmentload(LoginFragment())
        }

    }
    private fun fragmentload(fragment : Fragment)
    {
        val fragmentManager = supportFragmentManager
        val fragmentTransactionn= fragmentManager.beginTransaction()
        fragmentTransactionn.replace(R.id.authFrameLayout,fragment)
        fragmentTransactionn.commit()

    }
    override fun onBackPressed() {
        println("Hi there")
        val currentFragment = supportFragmentManager.fragments.last()
        super.onBackPressed()
        if(!currentFragment.toString().contains("DetailsFragment") ) {

            finish()
        }



    }
}