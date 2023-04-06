package com.example.grievifyadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.grievifyadmin.databinding.ActivityDescriptionBinding
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class DescriptionActivity : AppCompatActivity() {
    private lateinit var itemID:String
    private lateinit var binding: ActivityDescriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try {
            this.supportActionBar!!.hide()
        } // catch block to handle NullPointerException
        catch (_: NullPointerException) {
        }
        val extras = intent.extras
        if (extras != null) {
            itemID = extras.getString("key").toString()
            fetchDataFromDataBase(itemID)
        }
    }
    private fun fetchDataFromDataBase(items: String) {
        val databaseItem =
            FirebaseDatabase.getInstance()
                .getReference("tickets")

        databaseItem.child(items).get().addOnSuccessListener { dataSnapshot ->
            //Toast.makeText(applicationContext,dataSnapshot.child("userID").value.toString(),Toast.LENGTH_SHORT).show()
            println(dataSnapshot)
            if (dataSnapshot.exists()) {
                binding.ticketID.text=dataSnapshot.child("ticketID").value.toString()
                binding.username.text = dataSnapshot.child("userName").value.toString()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

                binding.severity.text = dataSnapshot.child("priority").value.toString()
                binding.status.text = dataSnapshot.child("status").value.toString()
                binding.descriptionHeading.text = dataSnapshot.child("description").value.toString()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                if(binding.status.text=="Resolved")
                {
                    binding.feedbackLayout.visibility= View.VISIBLE
                    binding.feedback.text = dataSnapshot.child("feedback").value.toString()
                }

                val uid = dataSnapshot.child("userUID").value
                fillUser(uid)


            }
        }.addOnFailureListener {
            TODO("Not yet implemented")
        }

    }

    private fun fillUser(uid: Any?) {
        binding.scholarID.text ="2112129"
        val databaseUser =
            FirebaseDatabase.getInstance()
                .getReference("Users")
        databaseUser.child(uid.toString()).get().addOnSuccessListener { snapshot ->

            if (snapshot.exists()) {
                binding.scholarID.text = snapshot.child("scholarID").value.toString()

            }
        }.addOnFailureListener {
            TODO("Not yet implemented")
        }

    }
}