package com.example.osrs.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import com.example.osrs.adapters.ProductsCustomListAdapter
import kotlinx.android.synthetic.main.activity_pre_login.*
import android.view.Menu
import android.view.MenuItem
import com.example.osrs.R
import com.example.osrs.services.BackendVolley
import com.example.osrs.services.ServiceVolley


class PreLoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_login)

        val myListAdapter = ServiceVolley().getAllProducts(this)
        mainProductsLV.adapter=myListAdapter


        // Configure action bar
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = ""


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
                //toast("Drawer closed")

            } // end onDrawerClosed

            override fun onDrawerOpened(drawerView: View){
                super.onDrawerOpened(drawerView)
                //toast("Drawer opened")

            } // end onDrawerOpened


        } // end drawerToggle: ActionBarDrawerToggle


        // Configure the drawer layout to add listener and show icon on toolbar
        drawerToggle.isDrawerIndicatorEnabled = true
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()


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



    // Extension function to show toast message easily
    private fun Context.toast(message:String){
        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
    } // end Context.toast



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the login_menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.login_menu,menu)
        return true
    } // end onCreateOptionsMenu



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.loginLogo) {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)

            return true
        }

        return super.onOptionsItemSelected(item)
    } // end onOptionsItemSelected

    
} // end PreLoginActivity