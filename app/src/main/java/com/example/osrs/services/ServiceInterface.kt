package com.example.osrs.services

import android.content.Context
import org.json.JSONObject

interface ServiceInterface {

    fun login(Email:String,Password:String,context: Context)

    fun singUp(Email:String,Password:String,FirstName:String,LastName:String,MobileNumber:String,UserType:Int,socialId:String,context: Context)

    fun loginFacebook(socialId:String,context: Context)

    fun addProduct(brandName:String,modelName:String,yearOfMake:String,
                   typeOfEngine:String,typeOfTransmission:String,price:Double,mileage:String,externalColor:String,
                   internalColor:String, description:String,
                   context: Context)

} // end ServiceInterface