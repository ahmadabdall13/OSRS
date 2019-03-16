package com.example.osrs.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.Toolbar
import android.text.InputType
import android.widget.DatePicker
import com.example.osrs.R
import kotlinx.android.synthetic.main.activity_add_product.*
import android.widget.Toast
import android.view.View
import android.widget.*







class AddProductActivity : AppCompatActivity() {
    var picker: DatePickerDialog? = null
    var years = Array<Any>(30,{ i -> 1990+i } )

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

//        fillYears()

        var mToolbar: Toolbar = findViewById(R.id.too)
        setSupportActionBar(mToolbar)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Add Product"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)


        addProductBtn.setOnClickListener {
            var CarInfo = hashMapOf<String,Any>()
            CarInfo["asdmkm"] = carBrandSpinner.selectedItem.toString()
            CarInfo["name"] = carModelSpinner.selectedItem.toString()
        } // end addProductBtn.setOnClickListener


        val array_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner_yearOfMakeEt!!.setAdapter(array_adapter)

    } // end onCreate



    fun fillYears() {
        for (i in 1980..2019) {
            years.fill(i)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    } // end onSupportNavigateUp
} // end AddProductActivity
