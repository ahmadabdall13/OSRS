package com.example.osrs.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import kotlinx.android.synthetic.main.activity_pre_login.*
import android.view.Menu
import android.view.MenuItem
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.osrs.Prefs
import com.example.osrs.R
import com.example.osrs.adapters.ProductsCustomListAdapter
import com.example.osrs.services.BackendVolley
import com.example.osrs.services.ServiceVolley
import org.json.JSONArray
import org.json.JSONObject

import android.support.v7.widget.SearchView
import android.widget.EditText
import com.android.volley.toolbox.JsonObjectRequest
import com.facebook.FacebookSdk
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.navigation_header.*


class PreLoginActivity : AppCompatActivity() {




    var carIds : ArrayList<Int> = arrayListOf()
    var carBrand : ArrayList<String> = arrayListOf()
    var carModle : ArrayList<String> = arrayListOf()

    var mainProductImage:ArrayList<String> = arrayListOf()
    var subProductImages  :ArrayList<String> = arrayListOf()


    var mileAge:ArrayList<Double> = arrayListOf()
    var trans: ArrayList<String> = arrayListOf()
    var carPrice:ArrayList<Double> = arrayListOf()

    val offerStatus:ArrayList<String> = arrayListOf()
    val adapterType:ArrayList<String> = arrayListOf()
    val vendors:ArrayList<JSONObject> = arrayListOf()
    var productTypes:ArrayList<Int> = arrayListOf()

    var predictedProductIds : ArrayList<Int> = arrayListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_login)

        FacebookSdk.sdkInitialize(this)


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

                    // new by zaidar
                    R.id.receivedRequests -> {
                        val intent = Intent(applicationContext, VendorRequestsActivity::class.java)
                        startActivity(intent)
                    }

                    R.id.possibleChats -> {
                        val intent = Intent(applicationContext, ChatListActivity::class.java)
                        startActivity(intent)

                    }

                    R.id.activeChats -> {
                        val intent = Intent(applicationContext, ActiveChatsActivity::class.java)
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



//        val searchItem = menu.findItem(R.id.searchBar)
//        searchView = searchItem.actionView as SearchView
//        searchView.setQueryHint("Search View Hint")




    } // end ofCreate


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
         val Prefs = Prefs(this)
        val ss =Prefs.userTypeId

        // Inflate the login_menu; this adds items to the action bar if it is present.
        if(Prefs.userTypeId == "null"){
            menuInflater.inflate(R.menu.login_menu,menu)
        }

        else{

            // put the user's name and pic in drawer
            menuInflater.inflate(R.menu.signout_menu,menu)
            userNameInDrawerTV.text = Prefs.firstName
            Picasso
                .with(this) // give it the context
                .load(Prefs.userImage) // load the image
                .into(userImageInDrawerTV)

        }



        val searchItem = menu.findItem(R.id.menu_search)
        if(searchItem != null){
            val searchView = searchItem.actionView as SearchView
            val editext = searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
            editext.hint = "Search here..."
            val carBrandFiltered : ArrayList<String> = arrayListOf()

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    carBrandFiltered.clear()
                    if(newText!!.isNotEmpty()){
                        for (i in carBrand) {
                            if(i.toLowerCase().contains(newText.toString())){
                                carBrandFiltered.add(i)
                            }
                        }

                        val myListAdapter = ProductsCustomListAdapter(
                            applicationContext,
                            carBrandFiltered,
                            carIds,
                            carModle,
                            mileAge,
                            trans,
                            carPrice,
                            mainProductImage,
                            offerStatus,
                            adapterType,
                            vendors,
                            productTypes,
                            subProductImages
                        )
                        mainProductsLV.adapter = myListAdapter

                    }else{
                        val myListAdapter = ProductsCustomListAdapter(
                            applicationContext,
                            carBrand,
                            carIds,
                            carModle,
                            mileAge,
                            trans,
                            carPrice,
                            mainProductImage,
                            offerStatus,
                            adapterType,
                            vendors,
                            productTypes,
                            subProductImages
                        )
                        mainProductsLV.adapter = myListAdapter

                    }

                    return true
                }

            })
        }

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






        fun getAllProducts(context: Context) {

            val basePath = "http://18.219.85.157/"
            val getAllProductsBasePath = "http://18.219.85.157/products"
            val TAG = ServiceVolley::class.java.simpleName

            var myListAdapter: ProductsCustomListAdapter


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
//                                mainProductImage.add(i,R.drawable.tesla1)
                                offerStatus.add(i,"")
                                vendors.add(i,vendor)
                                productTypes.add(i,jsonObject["product_type_id"].toString().toInt())

                                mainProductImage.add(i,if (jsonObject["image"] == null)  "null"
                                else jsonObject["image"].toString())


                                for(j in 0 until jsonArray.length()){
                                    val jsonInner: JSONObject = jsonArray.getJSONObject(j)
                                    subProductImages.add(i,jsonInner["image"].toString())
                                } // end for



                            } // end if
                            if(i == response.length()-1)
                                predectMostLikleyHood(this)

                        } // end for


                        myListAdapter = ProductsCustomListAdapter(
                            context,
                            carBrand,
                            carIds,
                            carModle,
                            mileAge,
                            trans,
                            carPrice,
                            mainProductImage,
                            offerStatus,
                            adapterType,
                            vendors,
                            productTypes,
                            subProductImages
                        )
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












        fun predectMostLikleyHood (context: Context){
        val Prefs = Prefs(this)
        val userId = Prefs.userId

        if(userId.toString() == "2"){

            val basePath = "http://18.219.85.157/"
            val getAllProductsBasePath = "http://18.219.85.157/predictions"
            val TAG = ServiceVolley::class.java.simpleName


            val jsonObjReq =
                object : JsonObjectRequest(Request.Method.GET,
                    getAllProductsBasePath,
                    null,
                    Response.Listener<JSONObject> { response ->

                        val getJSONArray = response.getJSONArray("predicted_products")
                        for (i in 0 until  getJSONArray.length() ){
                            predictedProductIds.add(getJSONArray.get(i).toString().toInt())

                            if(i == getJSONArray.length()-1 )
                               filterList(this)
                        } // end for

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

    }

   fun filterList(context:Context){

       for (i in 0 until  productTypes.size ){
           for (j in 0 until predictedProductIds.size){

                if(productTypes.get(i) == predictedProductIds.get(j)){


                    // set the item in the top of the stack
                    val carBrand1 = carBrand.get(i)
                    val carModle1 = carModle.get(i)
                    val imageId1 = mainProductImage.get(i)
                    val mileAge1 = mileAge.get(i)
                    val trans1 = trans.get(i)
                    val carPrice1 = carPrice.get(i)
                    val offerStatus1 = offerStatus.get(i)
                    val adapterType1 = adapterType.get(i)
                    val vendors1 = vendors.get(i)
                    val productType1 = productTypes.get(i)
                    carBrand.removeAt(i)
                    carModle.removeAt(i)
                    mainProductImage.removeAt(i)
                    mileAge.removeAt(i)
                    trans.removeAt(i)
                    carPrice.removeAt(i)
                    offerStatus.removeAt(i)
                    adapterType.removeAt(i)
                    vendors.removeAt(i)
                    productTypes.removeAt(i)



                    carBrand.add(0, carBrand1)
                    carModle.add(0, carModle1)
                    mainProductImage.add(0, imageId1)
                    mileAge.add(0, mileAge1)
                    trans.add(0, trans1)
                    carPrice.add(0, carPrice1)
                    vendors.add(0, vendors1)
                    offerStatus.add(0, offerStatus1)
                    adapterType.add(0, adapterType1)
                    vendors.add(0, vendors1)
                    productTypes.add(0, productType1)


                    val myListAdapter: ProductsCustomListAdapter = ProductsCustomListAdapter(
                        context,
                        carBrand,
                        carIds,
                        carModle,
                        mileAge,
                        trans,
                        carPrice,
                        mainProductImage,
                        offerStatus,
                        adapterType,
                        vendors,
                        productTypes,
                        subProductImages
                    )

                    mainProductsLV.adapter = myListAdapter


                }
           }
       } // end for


   }



     }