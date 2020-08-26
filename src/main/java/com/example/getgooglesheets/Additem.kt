package com.example.getgooglesheets

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.util.*

internal class AddItem : AppCompatActivity(), View.OnClickListener {
    var editTextItemName: EditText? = null
    var editTextBrand: EditText? = null
    var buttonAddItem: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_item)
        editTextItemName = findViewById<View>(R.id.et_item_name) as EditText
        editTextBrand = findViewById<View>(R.id.et_brand) as EditText
        buttonAddItem = findViewById<View>(R.id.btn_add_item) as Button
        buttonAddItem!!.setOnClickListener(this)
    }

    //This is the part where data is transafeered from Your Android phone to Sheet by using HTTP Rest API calls
    private fun addItemToSheet() {
        val loading = ProgressDialog.show(this, "Adding Item", "Please wait")
        val name = editTextItemName!!.text.toString().trim { it <= ' ' }
        val brand = editTextBrand!!.text.toString().trim { it <= ' ' }
        val stringRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                "https://script.google.com/macros/s/AKfycbwm7cYjnb35pADgtB3l38Vxa5kLNLhp5hcPyNV60UTaB5H33zgJ/exec",
                Response.Listener { response ->
                    loading.dismiss()
                    Toast.makeText(this@AddItem, response, Toast.LENGTH_LONG).show()
                    val intent =
                        Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                },
                Response.ErrorListener { }
            ) {
                override fun getParams(): Map<String, String> {
                    val parmas: MutableMap<String, String> =
                        HashMap()

                    //here we pass params
                    parmas["action"] = "addItem"
                    parmas["itemName"] = name
                    parmas["brand"] = brand
                    return parmas
                }
            }
        val socketTimeOut = 50000 // u can change this .. here it is 50 seconds
        val retryPolicy: RetryPolicy =
            DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        stringRequest.retryPolicy = retryPolicy
        val queue = Volley.newRequestQueue(this)
        queue.add(stringRequest)
    }

    override fun onClick(v: View) {
        if (v === buttonAddItem) {
            addItemToSheet()

            //Define what to do when button is clicked
        }
    }
}