package com.example.osrs.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.example.osrs.adapters.ProductsCustomListAdapter
import com.example.osrs.R
import kotlinx.android.synthetic.main.activity_my_requests.*

class MyRequestsActivity : AppCompatActivity() {

    private val CarBrand = arrayOf("Audi","BMW")
    private val CarModle = arrayOf(
        "A7",
        "Tiger"
    )

    private val imageId = arrayOf(
        R.drawable.audi,
        R.drawable.audi
    )

    private val MileAge:Array<Double> = arrayOf(
        13.0,0.0
    )

    private val Trans = arrayOf(
        "Auto",
        "Manual"
    )

    private val CarPrice:Array<Double> = arrayOf(
        700_000.213,13_22_13.22
    )

    private val OfferStatus:Array<String> = arrayOf(
        "Pending","Declined"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_requests)

        val myListAdapter = ProductsCustomListAdapter(
            this,
            CarBrand,
            CarModle,
            MileAge,
            Trans,
            CarPrice,
            imageId,
            OfferStatus
        )
        productsLV.adapter = myListAdapter

        var mToolbar: Toolbar = findViewById(R.id.too)
        setSupportActionBar(mToolbar)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "My Requests"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)


    } // end onCreate

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    } // end onSupportNavigateUp
} // end MyRequestsActivity
