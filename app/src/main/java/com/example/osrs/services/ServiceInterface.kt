package com.example.osrs.services

import android.content.Context
import android.view.View
import com.example.osrs.adapters.ProductsCustomListAdapter
import org.json.JSONObject

interface ServiceInterface {

    fun login(Email:String,Password:String,context: Context)

    fun singUp(Email:String,Password:String,FirstName:String,LastName:String,MobileNumber:String,UserType:Int,context: Context)

    fun getAllProducts(context:Context ) : ProductsCustomListAdapter

} // end ServiceInterface