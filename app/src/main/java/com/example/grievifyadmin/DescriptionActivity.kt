package com.example.grievifyadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


private lateinit var itemID:String
class DescriptionActivity : AppCompatActivity() {
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