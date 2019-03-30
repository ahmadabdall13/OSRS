package com.example.osrs.services

import android.content.Context
import android.view.View
import com.example.osrs.adapters.ProductsCustomListAdapter
import org.json.JSONArray
import org.json.JSONObject

interface ServiceInterface {

    fun login(Email:String,Password:String,context: Context)

    fun singUp(Email:String,Password:String,FirstName:String,LastName:String,MobileNumber:String,UserType:Int,socialId:String,context: Context)

    fun loginFacebook(socialId:String,context: Context)

    fun addProduct(brandName:String,modelName:String,yearOfMake:String,
                   typeOfEngine:String,typeOfTransmission:String,price:Double,mileage:Double,externalColor:String,
                   internalColor:String, description:String,
                   productTypeId:Long, vendorId:Int,
                   context: Context)


    fun createUserRequest(
        productId:Int,customer_id:Int,requestStatusId:Int,context: Context)


    fun deleteProduct(
        productId:Int,context: Context)


} // end ServiceInterface