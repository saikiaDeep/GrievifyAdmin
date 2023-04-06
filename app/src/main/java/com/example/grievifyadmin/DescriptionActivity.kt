package com.example.grievifyadmin

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.example.grievifyadmin.databinding.ActivityDescriptionBinding
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import android.content.Context
import android.util.Log
import com.example.grievifyadmin.dataClass.NotificationData
import com.example.grievifyadmin.dataClass.PushNotification
import com.example.grievifyadmin.notificationUtils.FirebaseService
import com.example.grievifyadmin.notificationUtils.RetrofitInstance
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
        binding.button2.setOnClickListener {
            showDialog(itemID)
        }
        binding.button5.setOnClickListener {
//            val recipientEmail = "example@example.com"
//            val emailSubject = "Need Immediate action"
//            val emailMessage = "Email message body goes here."
//
//            val intent = Intent(Intent.ACTION_SEND).apply {
//                type = "text/plain"
//                putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
//                putExtra(Intent.EXTRA_SUBJECT, emailSubject)
//                putExtra(Intent.EXTRA_TEXT, emailMessage)
//            }
//
//            startActivity(Intent.createChooser(intent, "Send email using:"))
            sendNotif()


        }
        binding.button4.setOnClickListener {
            FirebaseDatabase.getInstance().reference.child("tickets").child(itemID).child("status")
                .setValue("Resolved").addOnSuccessListener {
                    Toast.makeText(applicationContext,"Marked as resolved",Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }

        }
        binding.button3.setOnClickListener {
            FirebaseDatabase.getInstance().reference.child("tickets").child(itemID).child("status")
                .setValue("InProgress").addOnSuccessListener {
                    Toast.makeText(applicationContext,"Marked as in-progress",Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }

        }
    }
    val TOPIC = "/topics/myTopic2"
    private fun sendNotif() {
//        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
//        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
//            FirebaseService.token = it.token
//            etToken.setText(it.token)
//        }
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            FirebaseService.token = token
            // do something with the token, like save it to shared preferences
        }.addOnFailureListener { exception ->
            // handle the exception if the token retrieval fails
        }

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)


            val title = "title"
            val message = "etMessage.text.toString()"
            val recipientToken = "etToken.text.toString()"
            if(title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()) {
                PushNotification(
                    NotificationData(title, message),
                    recipientToken
                ).also {
                    sendNotification(it)
                }
            }
    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {
                Log.d(TAG, "Response: ${Gson().toJson(response)}")
            } else {
                Log.e(TAG, response.errorBody().toString())
            }
        } catch(e: Exception) {
            Log.e(TAG, e.toString())
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
                binding.button5.isEnabled=true
                if(binding.status.text=="InProgress")
                {
                    binding.button4.isEnabled=true
                }
                else if(binding.status.text=="Assigned")
                {
                    binding.button3.isEnabled=true
                }


            }
        }.addOnFailureListener {
            TODO("Not yet implemented")
        }


    }
    private fun showDialog(itemID:String) {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.send_message)
        dialog.setCancelable(true)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        val etPost = dialog.findViewById<EditText>(R.id.et_post)
        dialog.findViewById<View>(R.id.bt_cancel)
            .setOnClickListener { dialog.dismiss() }
        dialog.findViewById<View>(R.id.btn_submit).setOnClickListener { _: View? ->
            val customCat = etPost.text.toString().trim { it <= ' ' }
            FirebaseDatabase.getInstance().reference.child("tickets").child(itemID).child("resolvedMsg")
                .setValue(customCat).addOnSuccessListener {
                    Toast.makeText(applicationContext,"Message Posted",Toast.LENGTH_SHORT).show()
                }
            dialog.dismiss()
        }
        dialog.show()
        dialog.window!!.attributes = lp

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