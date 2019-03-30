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
import kotlinx.android.synthetic.main.activity_my_products.*
import kotlinx.android.synthetic.main.activity_pre_login.*
import org.json.JSONArray
import org.json.JSONObject

class MyProductsActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_products)


        getMyProducts(this)


        var mToolbar: Toolbar = findViewById(R.id.too)
        setSupportActionBar(mToolbar)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "My Products"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)


    } // end onCreate

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    } // end onSupportNavigateUp

























    fun getMyProducts(context: Context) {


        val Prefs = Prefs(this)

        val userId = Prefs.userId
        val userTypeId = Prefs.userTypeId

if(userTypeId.equals("2") && !userId.equals(" ")){

        val basePath = "http://18.219.85.157/"
        val getAllProductsBasePath = "http://18.219.85.157/users/${userId}/products"
        val TAG = ServiceVolley::class.java.simpleName

        var carIds : ArrayList<Int> = arrayListOf()
        var carBrand : ArrayList<String> = arrayListOf()
        var carModle : ArrayList<String> = arrayListOf()


     var imageId : ArrayList<Int> = arrayListOf()
        var mileAge:ArrayList<Double> = arrayListOf()

        var trans: ArrayList<String> = arrayListOf()

        var carPrice:ArrayList<Double> = arrayListOf()

        val offerStatus:ArrayList<String> = arrayListOf()
    val adapterType:ArrayList<String> = arrayListOf()
    val vendors:ArrayList<JSONObject> = arrayListOf()



        var myListAdapter : ProductsCustomListAdapter=ProductsCustomListAdapter(
            context,
            carBrand,
            carIds,
            carModle,
            mileAge,
            trans,
            carPrice,
            imageId,
            offerStatus,
            adapterType,
            vendors
        )


        val jsonObjReq =
            object : JsonArrayRequest(
                Request.Method.GET,
                getAllProductsBasePath,
                null,
                Response.Listener<JSONArray> { response ->


                    for (i in 0 until  response.length() ){
                        val jsonObject = response.getJSONObject(i)
                        if (jsonObject.has("id")){
                            carIds.add(i,jsonObject["id"].toString().toInt())
                            carBrand.add(i,jsonObject["brand_name"].toString())
                            carModle.add(i,jsonObject["model_name"].toString())
                            mileAge.add(i,jsonObject["mileage"].toString().toDouble())
                            trans.add(i,jsonObject["type_of_transmission"].toString())
                            carPrice.add(i,jsonObject["price"].toString().toDouble())
                            adapterType.add("products")
                            imageId.add(i,R.drawable.maserati)
                            offerStatus.add(i,"")
                            vendors.add(i,jsonObject)

                        } // end if
                    } // end for


                    val myListAdapter = ProductsCustomListAdapter(
                        context,
                        carBrand,
                        carIds,
                        carModle,
                        mileAge,
                        trans,
                        carPrice,
                        imageId,
                        offerStatus,
                        adapterType,
                        vendors
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




} // end MyProductsActivity
