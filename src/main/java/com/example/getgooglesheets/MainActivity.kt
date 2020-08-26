package com.example.getgooglesheets

import  com.example.getgooglesheets.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {
    var buttonAddItem: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonAddItem = findViewById<View>(R.id.add_item_btn) as Button
        buttonAddItem!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v === buttonAddItem) {
            val intent = Intent(applicationContext, AddItem::class.java)
            startActivity(intent)
        }
    }
}
