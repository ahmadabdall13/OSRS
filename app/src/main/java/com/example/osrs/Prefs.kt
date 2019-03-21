package com.example.osrs

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.preference.PreferenceManager


class Prefs(context: Context){
    companion object {
        private const val USER_ID = "USER_ID"
        private const val FIRST_NAME = "FIRST_NAME"
        private const val LAST_NAME = "LAST_NAME"
    }
     val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    // save USER_ID
    var userId = preferences.getString(USER_ID, "")
        set(value) = preferences.edit().putString(USER_ID,value).apply()



    var firstName = preferences.getString(FIRST_NAME, "")
        set(value) = preferences.edit().putString(FIRST_NAME,value).apply()




    var lastName = preferences.getString(LAST_NAME, "")
        set(value) = preferences.edit().putString(LAST_NAME,value).apply()


}