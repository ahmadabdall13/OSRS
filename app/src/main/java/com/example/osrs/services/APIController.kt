package com.example.osrs.services

import org.json.JSONObject

class APIController constructor(serviceInjection: ServiceInterface): ServiceInterface {
    private val service: ServiceInterface = serviceInjection

    override fun login(  ) {
        service.login(  )
    } // end fun post

} // end APIController