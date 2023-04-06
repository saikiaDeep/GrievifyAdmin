package com.example.grievifyadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.grievifyadmin.databinding.ActivityDescriptionBinding



class DescriptionActivity : AppCompatActivity() {
    private lateinit var itemID:String
    private lateinit var binding: ActivityDescriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
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

    private fun fetchDataFromDataBase(itemID: String) {

    }
}