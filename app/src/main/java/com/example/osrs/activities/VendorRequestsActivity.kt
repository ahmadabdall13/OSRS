package com.example.osrs.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.osrs.Prefs
import com.example.osrs.R
import com.example.osrs.adapters.VendorRequestAdapter
import com.example.osrs.services.BackendVolley
import com.example.osrs.services.ServiceVolley
import kotlinx.android.synthetic.main.activity_vendor_requests.*

import org.json.JSONArray
import org.json.JSONObject

class VendorRequestsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendor_requests)

        getRecievedRequests(this)


        val mToolbar: Toolbar = findViewById(R.id.toolbar_vendor_requests)
        setSupportActionBar(mToolbar)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Recieved Requests"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)





    }









    private fun getRecievedRequests(context: Context) {


        val Prefs = Prefs(this)

        val userId = Prefs.userId
        val userTypeId = Prefs.userTypeId

        if(userTypeId == "2" && userId != " "){

            val basePath = "http://18.219.85.157/"
            val getAllProductsBasePath = "http://18.219.85.157/users/$userId/orders"
            val TAG = ServiceVolley::class.java.simpleName


            val offers : ArrayList<String> = arrayListOf()
            val carIds : ArrayList<Int> = arrayListOf()
            val imageId : ArrayList<Int> = arrayListOf()
        val carBrand : ArrayList<String> = arrayListOf()
        val carPrice:ArrayList<Double> = arrayListOf()
            val usersNames:ArrayList<String> = arrayListOf()
            val vendors: ArrayList<JSONObject> = arrayListOf()
            val ids : ArrayList<Int> = arrayListOf()


            var myListAdapter: VendorRequestAdapter


            val jsonObjReq =
            object : JsonArrayRequest(
                Request.Method.GET,
                getAllProductsBasePath,
                null,
                Response.Listener<JSONArray> { response ->


                    for (i in 0 until  response.length() ){
                        val jsonObject = response.getJSONObject(i)
                        if (jsonObject.has("id")){

                            val product = jsonObject.getJSONObject("product")
                            ids.add(i,jsonObject["id"].toString().toInt())
                            carBrand.add(i,product["brand_name"].toString())
                            carIds.add(i,product["id"].toString().toInt())
                            imageId.add(i,R.drawable.man)
                            usersNames.add(i,"Zaidar Sallam")
                            carPrice.add(i,product["price"].toString().toDouble())
                            offers.add("#offer ${i+1}")
                            vendors.add(product.getJSONObject("vendor"))
                        } // end if
                    } // end for


                    myListAdapter = VendorRequestAdapter(
                        context,
                        carBrand,
                        carIds,
                        imageId,
                        usersNames,
                        carPrice,
                        offers,
                        vendors,
                        ids
                    )
                    recievedRequestsLV.adapter = myListAdapter


                },
                Response.ErrorListener {
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

    } // end getAllProducts


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    } // end onSupportNavigateUp


}
