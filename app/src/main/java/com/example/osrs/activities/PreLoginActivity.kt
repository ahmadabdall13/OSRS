package com.example.osrs.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_pre_login.*
import android.view.Menu
import android.view.MenuItem
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonArrayRequest
import com.example.osrs.Prefs
import com.example.osrs.R
import com.example.osrs.adapters.ProductsCustomListAdapter
import com.example.osrs.services.BackendVolley
import com.example.osrs.services.ServiceVolley
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject


class PreLoginActivity : AppCompatActivity() {


    var carIds : ArrayList<Int> = arrayListOf()
    var carBrand : ArrayList<String> = arrayListOf()
    var carModle : ArrayList<String> = arrayListOf()

    var imageId:ArrayList<String> = arrayListOf()
    var subProductImages  :ArrayList<String> = arrayListOf()

    var mileAge:ArrayList<Double> = arrayListOf()
    var trans: ArrayList<String> = arrayListOf()
    var carPrice:ArrayList<Double> = arrayListOf()

    val offerStatus:ArrayList<String> = arrayListOf()
    val adapterType:ArrayList<String> = arrayListOf()
    val vendors:ArrayList<JSONObject> = arrayListOf()
    var productTypes:ArrayList<Int> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_login)

        // Fetching All Products In The Database
        getAllProducts(this)

        // Configure action bar
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = ""
         val Prefs = Prefs(this)

        val userId = Prefs.userId
        val firstName = Prefs.firstName
        val lastName = Prefs.lastName
        val userTypeId = Prefs.userTypeId




        // Initialize the action bar drawer toggle instance
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ){
            override fun onDrawerClosed(view: View){
                super.onDrawerClosed(view)

            } // end onDrawerClosed

            override fun onDrawerOpened(drawerView: View){
                super.onDrawerOpened(drawerView)

            } // end onDrawerOpened


        } // end drawerToggle: ActionBarDrawerToggle


        // Configure the drawer layout to add listener and show icon on toolbar
        drawerToggle.isDrawerIndicatorEnabled = true
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()


        if(Prefs.userTypeId == "1"){
            navigation_view.menu.clear()
            navigation_view.inflateMenu(R.menu.customer_menu)
        }else if (Prefs.userTypeId == "2"){
            navigation_view.menu.clear()
            navigation_view.inflateMenu(R.menu.vendor_menu)
        }



        // Set navigation view navigation item selected listener
        navigation_view.setNavigationItemSelectedListener{

                // Handle navigation view item clicks here.
                when (it.itemId) {
                    R.id.addProductTab -> {
                        val intent = Intent(applicationContext, AddProductActivity::class.java)
                        startActivity(intent)
                    }

                    R.id.myRequestsTab -> {
                        val intent = Intent(applicationContext, MyRequestsActivity::class.java)
                        startActivity(intent)
                    }

                    R.id.myProductsTab -> {
                        val intent = Intent(applicationContext, MyProductsActivity::class.java)
                        startActivity(intent)
                    }

                    R.id.settingsTab -> {
                        val intent = Intent(applicationContext, SettingsActivity::class.java)
                        startActivity(intent)
                    }

                    R.id.contactUsTab -> {
                        val intent = Intent(applicationContext, ContactUsActivity::class.java)
                        startActivity(intent)
                    }

                    R.id.loginLogo -> {
                        val intent = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }
            // Close the drawer
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        } // end navigation_view.setNavigationItemSelectedListener



    } // end ofCreate


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
         val Prefs = Prefs(this)
        val ss =Prefs.userTypeId

        // Inflate the login_menu; this adds items to the action bar if it is present.
        if(Prefs.userTypeId == "")
            menuInflater.inflate(R.menu.login_menu,menu)
        else{
            menuInflater.inflate(R.menu.signout_menu,menu)
            userNameInDrawerTV.text = Prefs.firstName
            Picasso
                .with(this) // give it the context
                .load(Prefs.userImage) // load the image
                .into(userImageInDrawerTV)

        }// end else

        return true
    } // end onCreateOptionsMenu



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        val Prefs = Prefs(this)


        if (id == R.id.loginLogo) {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)



            return true
        }
        else if (id == R.id.signOutLoo){
            Prefs.userId = null
            Prefs.firstName = null
            Prefs.lastName = null
            Prefs.userTypeId = null
            val intent = Intent(applicationContext, PreLoginActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    } // end onOptionsItemSelected




    private fun getAllProducts(context: Context) {


        val getAllProductsBasePath = "http://18.219.85.157/products"
        val TAG = ServiceVolley::class.java.simpleName
        val myListAdapter =ProductsCustomListAdapter(
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
            vendors,
            productTypes,
            subProductImages
        )



        val jsonObjReq =
            object : JsonArrayRequest(Request.Method.GET,
                getAllProductsBasePath,
                null,
                Response.Listener<JSONArray> { response ->


                    for (i in 0 until  response.length() ){
                        val jsonObject = response.getJSONObject(i)
                        val vendor = jsonObject.getJSONObject("vendor")
                        val jsonArray: JSONArray = jsonObject.getJSONArray("images")
                        if (jsonObject.has("id")){
                            carIds.add(i,jsonObject["id"].toString().toInt())
                            carBrand.add(i,jsonObject["brand_name"].toString())
                            carModle.add(i,jsonObject["model_name"].toString())
                            mileAge.add(i,jsonObject["mileage"].toString().toDouble())
                            trans.add(i,jsonObject["type_of_transmission"].toString())
                            carPrice.add(i,jsonObject["price"].toString().toDouble())
                            adapterType.add("products")
                            imageId.add(i,jsonObject["image"].toString())
                            offerStatus.add(i,"")
                            vendors.add(i,vendor)
                            productTypes.add(i,jsonObject["product_type_id"].toString().toInt())

                            for(j in 0 until jsonArray.length()){
                                val jsonInner: JSONObject = jsonArray.getJSONObject(i)
                                subProductImages.add(i,jsonInner["image"].toString())

                            } // end for

                        } // end if

                    } // end for



                    mainProductsLV.adapter = myListAdapter


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

    } // end getAllProducts

} // end PreLoginActivity