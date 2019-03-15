package com.example.osrs.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.example.osrs.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        var mToolbar: Toolbar = findViewById(R.id.too)
        setSupportActionBar(mToolbar)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Sign Up"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        loginLink.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        } // end loginLink.setOnClickListener

    } // end onCreate

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    } // end onSupportNavigateUp
} // end SignUpActivity
