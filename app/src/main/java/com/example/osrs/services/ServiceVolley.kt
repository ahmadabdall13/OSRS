package com.example.osrs.services


import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.ListView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.osrs.Prefs
import com.example.osrs.R
import com.example.osrs.adapters.ProductsCustomListAdapter
import org.json.JSONArray
import com.example.osrs.activities.SignUpActivity
import com.example.osrs.activities.PreLoginActivity
import kotlinx.android.synthetic.main.activity_pre_login.*
import org.json.JSONObject

class ServiceVolley : ServiceInterface {



    val TAG = ServiceVolley::class.java.simpleName
    val basePath = "http://18.219.85.157/"
    val getAllProductsBasePath = "http://18.219.85.157/products"



    override fun login(Email: String, Password: String, context: Context) {

        val loginJO = JSONObject()
        loginJO.put("email_address", Email)
        loginJO.put("password", Password)
        val Prefs = Prefs(context)


        val jsonObjReq = object : JsonObjectRequest(
            Request.Method.POST, basePath + "login", loginJO,
            Response.Listener<JSONObject> { response ->


                //  Toast.makeText(context,"Welcome Vendor ${response["id"]}", Toast.LENGTH_LONG).show()
                var id = 0

                if (response.has("id")) {
                    val Prefs = Prefs(context)

                    Prefs.userId = response["id"].toString()
                    Prefs.firstName = response["first_name"].toString()
                    Prefs.lastName = response["last_name"].toString()
                    Prefs.userTypeId = response["user_type_id"].toString()

//                    val userId = Prefs.userId
//                    val firstName = Prefs.firstName
//                    val lastName = Prefs.lastName
                    val intent = Intent(context, PreLoginActivity::class.java)
                    context.startActivity(intent)
                } // end if
                else {
                    Toast.makeText(context, "Email Or Password Incorrect , Try Again", Toast.LENGTH_LONG).show()
                    return@Listener
                } // end else

            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                Toast.makeText(context, "Error : Sing The Fuck Up ${error.message}", Toast.LENGTH_LONG).show()


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





    override fun singUp(
        Email: String,
        Password: String,
        FirstName: String,
        LastName: String,
        MobileNumber: String,
        UserType: Int,
        socialId:String,
        context: Context
    ) {
        val signUpJO = JSONObject()
        signUpJO.put("email_address", Email)
        signUpJO.put("password", Password)
        signUpJO.put("first_name", FirstName)
        signUpJO.put("last_name", LastName)
        signUpJO.put("mobile_number", MobileNumber)
        signUpJO.put("user_type_id", UserType)
        signUpJO.put("social_id", socialId)


        val jsonObjReq = object : JsonObjectRequest(
            Request.Method.POST, basePath + "signup", signUpJO,
            Response.Listener<JSONObject> { response ->

                if (response.has("user_type_id")) {
                    Toast.makeText(
                        context, "Registration Completed Successfully, Welcome ${response["first_name"]} "
                        , Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(context, PreLoginActivity::class.java)
                    context.startActivity(intent)
                }
                else{
                    Toast.makeText(context,"Please Sign The Fuck Up", Toast.LENGTH_LONG).show()
                }


            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                Toast.makeText(context, "Error : Sing The Fuck Up ${error.message}", Toast.LENGTH_LONG).show()


            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }

        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)

    } // end ServiceVolley


    override fun loginFacebook(socialId: String, context: Context) {

        val loginJO = JSONObject()
        loginJO.put("social_id", socialId)


        val jsonObjReq = object : JsonObjectRequest(
            Request.Method.POST, basePath + "loginFacebook", loginJO,
            Response.Listener<JSONObject> { response ->

                if (response.has("id")) {
                    Toast.makeText(context, "hahah congrats! {$response}", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(context, "Please Sign The Fuck Up", Toast.LENGTH_LONG).show()
//                    val intent = Intent(context, SignUpActivity::class.java)
//                    intent.putExtra("social_id", socialId)
//                    context.startActivity(intent)

                    val intent = Intent(context, SignUpActivity::class.java)
                    intent.putExtra("social_id", socialId)

                    context.startActivity(intent)

                }

            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                Toast.makeText(context, "Error : Sing The Fuck Up ${error.message}", Toast.LENGTH_LONG).show()


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









    override fun addProduct(
        brandName:String,modelName:String,yearOfMake:String,
        typeOfEngine:String,typeOfTransmission:String,price:Double,mileage:String,externalColor:String,
        internalColor:String, description:String,
        context: Context
    ) {
        val productOj = JSONObject()
        productOj.put("brand_name", brandName)
        productOj.put("model_name", modelName)
        productOj.put("year_of_make", yearOfMake)
        productOj.put("type_of_engine", typeOfEngine)
        productOj.put("price", price)
        productOj.put("mileage", mileage)
        productOj.put("external_color", externalColor)
        productOj.put("internal_color", internalColor)
        productOj.put("description", description)
        productOj.put("product_type_id", 3)
        productOj.put("vendor_id", 17)



        val jsonObjReq = object : JsonObjectRequest(
            Request.Method.POST, basePath + "products", productOj,
            Response.Listener<JSONObject> { response ->

                Toast.makeText(
                    context, ", Welcome ${response} "
                    , Toast.LENGTH_LONG
                ).show()
            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                Toast.makeText(context, "Error : Sing The Fuck Up ${error.message}", Toast.LENGTH_LONG).show()


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


    } // end ServiceVolley