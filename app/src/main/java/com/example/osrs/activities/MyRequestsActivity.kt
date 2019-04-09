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
import com.example.osrs.adapters.ProductsCustomListAdapter
import com.example.osrs.R
import com.example.osrs.services.BackendVolley
import com.example.osrs.services.ServiceVolley
import kotlinx.android.synthetic.main.activity_my_requests.*
import kotlinx.android.synthetic.main.activity_pre_login.*
import org.json.JSONArray
import org.json.JSONObject

class MyRequestsActivity : AppCompatActivity() {

//    private val carIds : ArrayList<Int> = arrayListOf(1,2)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_requests)


        val mToolbar: Toolbar = findViewById(R.id.too)
        setSupportActionBar(mToolbar)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "My Requests"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)



        getAllRequestsAsAUser(applicationContext)


    } // end onCreate

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    } // end onSupportNavigateUp







    fun getAllRequestsAsAUser(context: Context) {
        val Prefs = Prefs(this)

        if(Prefs.userId != null && Prefs.userId != "") {


            val basePath = "http://18.219.85.157/"
            val getAllProductsBasePath = "http://18.219.85.157/users/${Prefs.userId}/requests"
            val TAG = ServiceVolley::class.java.simpleName

            var carIds: ArrayList<Int> = arrayListOf()
            var carBrand: ArrayList<String> = arrayListOf()
            var carModle: ArrayList<String> = arrayListOf()

//            val imageIdArray = arrayOf(
//                R.drawable.audi,
//            )

            var imageIdArray: ArrayList<Int> = arrayListOf()
            var mileAge: ArrayList<Double> = arrayListOf()

            var trans: ArrayList<String> = arrayListOf()

            var carPrice: ArrayList<Double> = arrayListOf()

            val offerStatus: ArrayList<String> = arrayListOf()
            val adapterType: ArrayList<String> = arrayListOf()
            val vendors: ArrayList<JSONObject> = arrayListOf()
            var productTypes:ArrayList<Int> = arrayListOf()



            var myListAdapter: ProductsCustomListAdapter = ProductsCustomListAdapter(
                context,
                carBrand,
                carIds,
                carModle,
                mileAge,
                trans,
                carPrice,
                imageIdArray,
                offerStatus,
                adapterType,
                vendors,
                productTypes
            )


            val jsonObjReq =
                object : JsonArrayRequest(
                    Request.Method.GET,
                    getAllProductsBasePath,
                    null,
                    Response.Listener<JSONArray> { response ->


                        for (i in 0 until response.length()) {
                            val jsonObject = response.getJSONObject(i)
                            if (jsonObject.has("id")) {
                                val product = jsonObject.getJSONObject("product")
                                val request_status = jsonObject.getJSONObject("request_status")
                                carIds.add(i, product["id"].toString().toInt())
                                carBrand.add(i, product["brand_name"].toString())
                                carModle.add(i, product["model_name"].toString())
                                mileAge.add(i, product["mileage"].toString().toDouble())
                                trans.add(i, product["type_of_transmission"].toString())
                                carPrice.add(i, product["price"].toString().toDouble())
                                offerStatus.add(i, request_status["status"].toString())
                                adapterType.add("user_request_adapter")
                                imageIdArray.add(i,R.drawable.audi)
                                vendors.add(i, product.getJSONObject("vendor"))
                                productTypes.add(i,product["product_type_id"].toString().toInt())



                            } // end if
                        } // end for


                        val myListAdapter = ProductsCustomListAdapter(
                            this,
                            carBrand,
                            carIds,
                            carModle,
                            mileAge,
                            trans,
                            carPrice,
                            imageIdArray,
                            offerStatus,
                            adapterType,
                            vendors,
                            productTypes
                        )
                        productsLV.adapter = myListAdapter

                    },
                    Response.ErrorListener { error ->
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

} // end MyRequestsActivity
