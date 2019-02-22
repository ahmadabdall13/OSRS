package com.example.osrs.activities.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.osrs.activities.PreLoginActivity
import com.example.osrs.activities.ProductsCustomListAdapter
import kotlinx.android.synthetic.main.fragment_products.*
import android.content.Intent
import com.example.osrs.R


class ProductsFragment  : Fragment() {
    private val language = arrayOf("Audi","5o5a")
    private val description = arrayOf(
        "One of the fastest cars on the road",
        "The Best Car in the World"
    )

    private val imageId = arrayOf(
        R.drawable.audi,
        R.drawable.audi
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
                val myListAdapter = ProductsCustomListAdapter(context,language,description,imageId)
        productsLV.adapter = myListAdapter



    }

}