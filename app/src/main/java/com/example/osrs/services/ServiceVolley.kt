package com.example.osrs.services


import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
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
        ProfilePic:String,
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
        signUpJO.put("profile_picture",ProfilePic)
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
                    Toast.makeText(context,"Something Went Wrong You're Not Registered", Toast.LENGTH_LONG).show()
                }


            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                Toast.makeText(context, "Error : Registration Failed ${error.message}", Toast.LENGTH_LONG).show()


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
        productMainImage :String, subImages: java.util.HashMap<String, String>, brandName:String, modelName:String, yearOfMake:String,
        typeOfEngine:String, typeOfTransmission:String, price:Double, mileage:Double, externalColor:String,
        internalColor:String, description:String,
        productTypeId:Long, vendorId:Int,
        context: Context
    ) {
        val subImagesArrayList : java.util.HashMap<String, String> = subImages
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
        productOj.put("product_type_id", productTypeId)
        productOj.put("vendor_id", vendorId)
        productOj.put("type_of_transmission", typeOfTransmission)
        productOj.put("image", productMainImage)
//        productOj.put("images", subImagesArrayList.values)

        Toast.makeText(
            context, ""+ JSONArray(subImagesArrayList.values)
            , Toast.LENGTH_LONG
        ).show()


        val jsonObjReq = object : JsonObjectRequest(
            Request.Method.POST, basePath + "products", productOj,
            Response.Listener<JSONObject> { response ->

                Toast.makeText(
                    context, "Product Added Successfully "
                    , Toast.LENGTH_LONG
                ).show()
            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                Toast.makeText(context, "Error While Adding Product ${error.message}", Toast.LENGTH_LONG).show()
                Log.e(TAG,error.message)
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




    override fun createUserRequest(
        productId:Int,customerId:Int,requestStatusId:Int,
        context: Context
    ) {
        val productOj = JSONObject()
        productOj.put("product_id", productId)
        productOj.put("customer_id", customerId)
        productOj.put("request_status_id", 1)



        val jsonObjReq = object : JsonObjectRequest(
            Request.Method.POST, basePath + "requests", productOj,
            Response.Listener<JSONObject> { response ->

                Toast.makeText(
                    context, ", Added to your requests , thank you ...  ${response} "
                    , Toast.LENGTH_LONG
                ).show()


            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                Toast.makeText(context, "Error :  ${error.message}", Toast.LENGTH_LONG).show()


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














    override fun deleteProduct(
        productId:Int,
        context: Context
    ) {

        val jsonObjReq = object : JsonObjectRequest(
            Request.Method.PUT, basePath + "products/${productId}/to_deleted", null,
            Response.Listener<JSONObject> { response ->

                Toast.makeText(
                    context, ", has been deleted , thank you ...  ${response} "
                    , Toast.LENGTH_LONG
                ).show()
            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/post request : ${error.message}")
                Toast.makeText(context, "Error :  ${error.message}", Toast.LENGTH_LONG).show()


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






    override fun likeProductType(
        productTypeId:Int,userId:Int,context: Context
    ) {
        val productOj = JSONObject()
        productOj.put("product_type_id", productTypeId)
        productOj.put("user_id", userId)

        val jsonObjReq = object : JsonObjectRequest(
            Request.Method.POST, basePath + "user_favourites", productOj,
            Response.Listener<JSONObject> { response ->

                Toast.makeText(
                    context, "Thank you , We will help you to find the suitable products "
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
    } // end addProduct





    override fun sendMsg(
        msg:String,userId:Int,channel_id:Int,context: Context
    ) {
        val productOj = JSONObject()
        productOj.put("msg", msg)
        productOj.put("user_id", userId)
        productOj.put("channel_id", channel_id)


        val jsonObjReq = object : JsonObjectRequest(
            Request.Method.POST, basePath + "chats", productOj,
            Response.Listener<JSONObject> { response ->

                Toast.makeText(
                    context, "Your msg has been sent . "
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
    } // end addProduct




    override fun hireRequest(
    requestId:Int,context: Context
    ) {

        val jsonObjReq = object : JsonObjectRequest(
            Request.Method.PUT, basePath + "requests/${requestId}/to_completed", null,
            Response.Listener<JSONObject> { response ->

                Toast.makeText(
                    context, ", has been hired , thank you ...  ${response} "
                    , Toast.LENGTH_LONG
                ).show()
            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/post request : ${error.message}")
//                Toast.makeText(context, "Error :  ${error.message}", Toast.LENGTH_LONG).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }

        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    } // end






    override fun declineRequest(
        requestId:Int,context: Context
    ) {

        val jsonObjReq = object : JsonObjectRequest(
            Request.Method.PUT, basePath + "requests/${requestId}/to_cancelled", null,
            Response.Listener<JSONObject> { response ->
                Toast.makeText(
                    context, ", has been declined , thank you ...  ${response} "
                    , Toast.LENGTH_LONG
                ).show()
            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/post request : ${error.message}")
//                Toast.makeText(context, "Error :  ${error.message}", Toast.LENGTH_LONG).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }

        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    } // end

} // end ServiceVolley