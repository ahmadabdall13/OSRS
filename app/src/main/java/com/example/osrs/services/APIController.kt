package com.example.osrs.services

import android.content.Context


class APIController constructor(serviceInjection: ServiceInterface): ServiceInterface {
    private val service: ServiceInterface = serviceInjection

    override fun login(Email:String,Password:String,context: Context) {
        service.login(Email,Password,context)
    } // end fun login

} // end APIController