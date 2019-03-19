package com.example.osrs.services

import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject



class ServiceVolley : ServiceInterface {
    val TAG = ServiceVolley::class.java.simpleName
    val basePath = "http://18.219.85.157:5000/"

    override fun login( ) {

        val ahOBJ = JSONObject()
        ahOBJ.put("email_address", "asd")
        ahOBJ.put("password", "asd")

        val jsonObjReq = object : JsonObjectRequest(
            Request.Method.POST, basePath + "login", ahOBJ,
            Response.Listener<JSONObject> { response ->
                Log.d(TAG, "/post request OK! Response: $response")

//                completionHandler(response)
            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
//                completionHandler(null)
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }
        }

        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    }
} // end ServiceVolley