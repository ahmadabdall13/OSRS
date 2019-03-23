package com.example.osrs.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import android.widget.Toast
import com.example.osrs.adapters.ProductsCustomListAdapter
import kotlinx.android.synthetic.main.activity_pre_login.*
import android.view.Menu
import android.view.MenuItem
import com.example.osrs.Prefs
import com.example.osrs.R


class PreLoginActivity : AppCompatActivity() {

    private val CarBrand = arrayOf("Audi","BMW")
    private val CarModle = arrayOf(
        "A7",
        "Tiger"
    )

    private val imageId = arrayOf(
        R.drawable.audi,
        R.drawable.audi
    )

    private val MileAge:Array<Double> = arrayOf(
        13.0,0.0
    )

    private val Trans = arrayOf(
        "Auto",
        "Manual"
    )

    private val CarPrice:Array<Double> = arrayOf(
        700_000.213,13_22_13.22
    )

    private val OfferStatus:Array<String> = arrayOf(
        "Pending","Declined"
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_login)


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


        if(Prefs.userTypeId.equals("1")){
            navigation_view.menu.clear()
            navigation_view.inflateMenu(R.menu.customer_menu)
        }else if (Prefs.userTypeId.equals("2")){
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

        val myListAdapter = ProductsCustomListAdapter(
            this,
            CarBrand,
            CarModle,
            MileAge,
            Trans,
            CarPrice,
            imageId,
            OfferStatus
        )
        productsLV.adapter = myListAdapter
    } // end ofCreate



    // Extension function to show toast message easily
    private fun Context.toast(message:String){
    } // end Context.toast



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
         val Prefs = Prefs(this)
        val ss =Prefs.userTypeId

        // Inflate the login_menu; this adds items to the action bar if it is present.
        if(Prefs.userTypeId == "")
            menuInflater.inflate(R.menu.login_menu,menu)
        else
            menuInflater.inflate(R.menu.signout_menu,menu)

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

    
} // end PreLoginActivity