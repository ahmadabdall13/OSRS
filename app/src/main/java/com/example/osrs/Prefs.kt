package com.example.osrs

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color

class Prefs constructor(context: Context){

    private val loginPreference = "login info"
    val loginSharedPref: SharedPreferences = context.getSharedPreferences(loginPreference, 0) // prefs


    val user_id = "user_id"
//    //    Here create vars to store the response in
    var userID: Int
        get() = loginSharedPref.getInt(user_id, Color.BLACK)
        set(value) = loginSharedPref.edit().putInt(user_id,value).apply()


} // end Prefs