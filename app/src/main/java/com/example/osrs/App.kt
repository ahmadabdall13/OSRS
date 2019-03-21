package com.example.osrs

import android.app.Application

val loginSharedPref: Prefs by lazy {
    App.loginSharedPref!!
}


class App : Application() {

    companion object {
        var loginSharedPref: Prefs? = null
    }

    override fun onCreate() {

        loginSharedPref = Prefs(applicationContext)
        super.onCreate()

    } // end onCreate


} // end App class