package com.example.osrs.services


import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.example.osrs.activities.SignUpActivity
import com.example.osrs.loginSharedPref


import org.json.JSONObject



class ServiceVolley : ServiceInterface {
    val TAG = ServiceVolley::class.java.simpleName
    val basePath = "http://18.219.85.157:5000/"



    override fun login(Email:String,Password:String,context: Context) {

        val loginJO = JSONObject()
        loginJO.put("email_address", Email)
        loginJO.put("password", Password)


        val jsonObjReq = object : JsonObjectRequest(
            Request.Method.POST, basePath + "login", loginJO,
            Response.Listener<JSONObject> { response ->

            //    loginSharedPref.userID = response.getInt("user_type_id")

//                if (response.getBoolean("user_type_id")){
//                    val intent = Intent(context, SignUpActivity::class.java)
//                    context.startActivity(intent)
//                }else{
                val type = response["user_type_id"]

                if ( type != null){
                    Toast.makeText(context,"Welcome Vendor ${response["id"]}", Toast.LENGTH_LONG).show()
                val intent = Intent(context, SignUpActivity::class.java)
                    context.startActivity(intent)}
                else{
                    Toast.makeText(context,"Please Sign The Fuck Up", Toast.LENGTH_LONG).show()

                }
//                }

            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                Toast.makeText(context,"Error : Sing The Fuck Up ${error.message}", Toast.LENGTH_LONG).show()


            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }

        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    }
} // end ServiceVolley