package com.example.osrs.services

import android.content.Context
import com.example.osrs.adapters.ProductsCustomListAdapter
import org.json.JSONArray


class APIController constructor(serviceInjection: ServiceInterface , productsCustomListAdapter: ProductsCustomListAdapter): ServiceInterface {

    val pcla = JSONArray()
    private val service: ServiceInterface = serviceInjection




    override fun login(Email:String,Password:String,context: Context) {
        service.login(Email,Password,context)
    } // end fun login


    override fun singUp(
        Email: String,
        Password: String,
        FirstName: String,
        LastName: String,
        MobileNumber: String,
        UserType:Int,
        socialId:String,
        context: Context
    ) {
        service.singUp(Email,Password,FirstName,LastName, MobileNumber,UserType,socialId,context)
    } // end singUp



    override fun loginFacebook(
        socialId: String,
        context: Context
    ) {
        service.loginFacebook(socialId,context)
    } // end singUp


    override fun addProduct(
        brandName:String,modelName:String,yearOfMake:String,
        typeOfEngine:String,typeOfTransmission:String,price:Double,mileage:String,externalColor:String,
        internalColor:String, description:String,
        context: Context
    ) {
        service.addProduct(brandName,modelName,yearOfMake,typeOfEngine,
            typeOfTransmission,price,mileage,externalColor,internalColor,description,context)
    } // end singUp


} // end APIController