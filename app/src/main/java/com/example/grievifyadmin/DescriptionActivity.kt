package com.example.grievifyadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
                binding.status.text = dataSnapshot.child("userID").value.toString()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                binding.productCondition.text = dataSnapshot.child("userName").value.toString()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                binding.descriptionHeading.text = dataSnapshot.child("description").value.toString()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                var category = dataSnapshot.child("priority").value.toString()
                binding.productCategory.text = category
                val date = "Selling Date: " + dataSnapshot.child("sellingDate").value.toString()

                var useTime = dataSnapshot.child("usedForTime").value.toString()
                if (useTime == "") {
                    useTime = "-"
                }
                binding.productUsedFor.text = useTime
                val uid = dataSnapshot.child("userUID").value
                fillUser(uid)


            }
        }.addOnFailureListener {
            TODO("Not yet implemented")
        }

    }

    private fun fillUser(uid: Any?) {
        val databaseUser =
            FirebaseDatabase.getInstance("https://sellr-7a02b-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Users")
        databaseUser.child(uid.toString()).get().addOnSuccessListener { snapshot ->

            if (snapshot.exists()) {
                val email = snapshot.child("email").value.toString()
                val phone = snapshot.child("phonenum").value.toString()
                val name = snapshot.child("name").value.toString()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
//                binding.sellerName.text = name
//                binding.sellerEmail.text = email
//                binding.sellerPhone.text = phone
//                phoneSeller = phone
//                emailSeller = email
            }
        }.addOnFailureListener {
            TODO("Not yet implemented")
        }

    }
}