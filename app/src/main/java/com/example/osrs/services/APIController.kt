package com.example.osrs.services

import android.content.Context
import com.example.osrs.adapters.ProductsCustomListAdapter
import org.json.JSONArray


class APIController constructor(serviceInjection: ServiceInterface , productsCustomListAdapter: ProductsCustomListAdapter): ServiceInterface {

    val pcla = productsCustomListAdapter
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
        context: Context
    ) {
        service.singUp(Email,Password,FirstName,LastName, MobileNumber,UserType,context)
    } // end singUp


    override fun getAllProducts(context:Context ) : ProductsCustomListAdapter {
        service.getAllProducts(context)
        return pcla
    } // end getAllProducts

} // end APIController