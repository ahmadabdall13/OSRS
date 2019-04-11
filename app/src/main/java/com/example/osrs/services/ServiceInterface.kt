package com.example.osrs.services

import android.content.Context
import android.view.View
import com.example.osrs.adapters.ProductsCustomListAdapter
import org.json.JSONArray
import org.json.JSONObject

interface ServiceInterface {

    fun login(Email:String,Password:String,context: Context)

    fun singUp(ProfilePic:String,Email:String,Password:String,FirstName:String,LastName:String,MobileNumber:String,UserType:Int,socialId:String,context: Context)



    fun loginFacebook(socialId:String,context: Context)

    fun addProduct(productMainImage :String , subImages:MutableList<String>,brandName:String,modelName:String,yearOfMake:String,
                   typeOfEngine:String,typeOfTransmission:String,price:Double,mileage:Double,externalColor:String,
                   internalColor:String, description:String,
                   productTypeId:Long, vendorId:Int,
                   context: Context)



    fun createUserRequest(
        productId:Int,customer_id:Int,requestStatusId:Int,context: Context)


    fun deleteProduct(
        productId:Int,context: Context)


    fun likeProductType(
        productTypeId:Int,userId:Int,context: Context)



    fun sendMsg(
        msg:String,userId:Int,channel_id:Int,context: Context)


    fun hireRequest(
        requestId:Int,context: Context)

    fun declineRequest(
        requestId:Int,context: Context)


} // end ServiceInterface