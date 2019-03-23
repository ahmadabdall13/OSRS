package com.example.osrs.services


import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.osrs.activities.PreLoginActivity
import org.json.JSONObject
import com.example.osrs.Prefs
import com.example.osrs.R
import com.example.osrs.adapters.ProductsCustomListAdapter
import org.json.JSONArray


class ServiceVolley : ServiceInterface {



    val TAG = ServiceVolley::class.java.simpleName
    val basePath = "http://18.219.85.157/"
    val getAllProductsBasePath = "http://18.219.85.157/products"


    override fun login(Email:String,Password:String,context: Context) {

        val loginJO = JSONObject()
        loginJO.put("email_address", Email)
        loginJO.put("password", Password)


        val jsonObjReq = object : JsonObjectRequest(
            Request.Method.POST, basePath + "login", loginJO,
            Response.Listener<JSONObject> { response ->

                var id =0

                if (response.has("id")){
                     id = response["id"].toString().toInt()
                } // end if
                else {
                    Toast.makeText(context,"Email Or Password Incorrect , Try Again", Toast.LENGTH_LONG).show()
                    return@Listener
                } // end else


//                if ( id > 0){
                    val Prefs = Prefs(context)

                    Prefs.userId = response["id"].toString()
                    Prefs.firstName = response["first_name"].toString()
                    Prefs.lastName = response["last_name"].toString()

                    val userId = Prefs.userId
                    val firstName = Prefs.firstName
                    val lastName = Prefs.lastName

                    Toast.makeText(context,"===> $firstName $lastName $userId", Toast.LENGTH_LONG).show()

                    val intent = Intent(context, PreLoginActivity::class.java)
                    context.startActivity(intent)

//                }
//                else{
//                    Toast.makeText(context,"Please Sign The Fuck Up", Toast.LENGTH_LONG).show()
//
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
    } // end login

    override fun singUp(
        Email: String,
        Password: String,
        FirstName: String,
        LastName: String,
        MobileNumber: String,
        UserType:Int,
        context: Context
    ) {
        val signUpJO = JSONObject()
        signUpJO.put("email_address", Email)
        signUpJO.put("password", Password)
        signUpJO.put("first_name", FirstName)
        signUpJO.put("last_name", LastName)
        signUpJO.put("mobile_number", MobileNumber)
        signUpJO.put("user_type_id", UserType)


        val jsonObjReq = object : JsonObjectRequest(
            Request.Method.POST, basePath + "signup", signUpJO,
            Response.Listener<JSONObject> { response ->

                    Toast.makeText(context,"Registration Completed Successfully, Welcome ${response["first_name"]} "
                        , Toast.LENGTH_LONG).show()
                    val intent = Intent(context, PreLoginActivity::class.java)
                    context.startActivity(intent)

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
    } // end singUp


    override fun getAllProducts(context: Context): ProductsCustomListAdapter {
        var myListAdapter : ProductsCustomListAdapter

        var carBrand : ArrayList<String> = arrayListOf()
        var carModle : ArrayList<String> = arrayListOf()

        val imageId = arrayOf(
            R.drawable.audi,
            R.drawable.audi
        )

        var mileAge:ArrayList<Double> = arrayListOf()

        var trans: ArrayList<String> = arrayListOf()

        var carPrice:ArrayList<Double> = arrayListOf()

        val offerStatus:ArrayList<String> = arrayListOf("Pending","Canceled")

        var jsonArray = JSONArray()
        val jsonObjReq =
        object : JsonArrayRequest(Request.Method.GET,
            getAllProductsBasePath,
            null,
            Response.Listener<JSONArray> { response ->


                jsonArray = response



                for (i in 0 until  response.length() ){
                    val jsonObject = response.getJSONObject(i)
                        if (jsonObject.has("id")){
                            carBrand.add(i,jsonObject["brand_name"].toString())
                            carModle.add(i,jsonObject["model_name"].toString())
                            mileAge.add(i,jsonObject["mileage"].toString().toDouble())
                            trans.add(i,jsonObject["type_of_transmission"].toString())
                            carPrice.add(i,jsonObject["price"].toString().toDouble())

                        } // end if

                } // end for

            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }
        myListAdapter = ProductsCustomListAdapter(
            context,
            carBrand,
            carModle,
            mileAge,
            trans,
            carPrice,
            imageId,
            offerStatus
        )
        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)

        return myListAdapter

    } // end getAllProducts


} // end ServiceVolley