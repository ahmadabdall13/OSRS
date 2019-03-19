package com.example.osrs.services

import org.json.JSONObject

interface ServiceInterface {

    fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)

} // end ServiceInterface