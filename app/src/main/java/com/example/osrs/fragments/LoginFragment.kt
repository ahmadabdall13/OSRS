package com.example.osrs.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.osrs.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import java.util.*

class LoginFragment: Fragment() {
    private var callbackManager: CallbackManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_login, container, false)
        FacebookSdk.sdkInitialize(context)
        AppEventsLogger.activateApp(context)


        var btnLoginFacebook = view.findViewById<Button>(R.id.login_facebook_button)

        btnLoginFacebook.setOnClickListener(View.OnClickListener {
            // Login
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        Log.d("Batool BITCH", "Facebook token: " + loginResult.accessToken.token)
                        Log.d("Batool FUCK HER PUSSY", "Facebook userId: " + loginResult.accessToken.userId)

                    }

                    override fun onCancel() {
                        Log.e("Batool", "Facebook onCancel.")

                    }

                    override fun onError(error: FacebookException) {
                        Log.e("Batool", "Facebook onError."+error)

                    }
                })
        })


        return view}





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }


} // end LoginFragment