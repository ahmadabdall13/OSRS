package com.example.osrs.activities

import android.content.Intent

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.osrs.R
import com.example.osrs.services.ServiceVolley

import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import com.example.osrs.Prefs

class LoginActivity : AppCompatActivity() {

    private var callbackManager: CallbackManager? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        FacebookSdk.sdkInitialize(this)
        AppEventsLogger.activateApp(this)
        val Prefs =Prefs(this)

        val btnLoginFacebook = findViewById<Button>(R.id.login_facebook_button)

        btnLoginFacebook.setOnClickListener {
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {

                        Log.d("BITCH", "Facebook token: " + loginResult.accessToken.token)
                        Log.d("FUCK HER PUSSY", "Facebook userId: " + loginResult.accessToken.userId)
                        //ServiceVolley().loginFacebook(loginResult.accessToken.userId , applicationContext)
                    }

                    override fun onCancel() {
                        Log.e("CancelFB", "Facebook onCancel.")
                    }

                    override fun onError(error: FacebookException) {
                        Log.e("ErorFB", "Facebook onError.$error")
                    }

                })

        } // end

        logInBTN.setOnClickListener {

            ServiceVolley().login( emailAddressET.text.toString(),passwordET.text.toString(),this)

        } // end logInBTN.setOnClickListener

        toSignUpActivityBTN.setOnClickListener {

            val userId = Prefs.userId
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            intent.putExtra("social_id","default_singup")
            startActivity(intent)

        } // end signInBTN.setOnClickListener

        val mToolbar: Toolbar = findViewById(R.id.too)
        setSupportActionBar(mToolbar)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Sign In"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)




    } // end onCreate

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    } // end onSupportNavigateUp

} // end LoginActivity
