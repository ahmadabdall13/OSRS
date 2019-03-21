package com.example.osrs.services

import android.content.Context
import org.json.JSONObject

interface ServiceInterface {

    fun login(Email:String,Password:String,context: Context)

} // end ServiceInterface