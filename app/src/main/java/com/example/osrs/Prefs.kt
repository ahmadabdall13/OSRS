package com.example.osrs

import android.content.Context
import android.preference.PreferenceManager


class Prefs(context: Context){
    companion object {
        private const val USER_ID = "USER_ID"
        private const val FIRST_NAME = "FIRST_NAME"
        private const val LAST_NAME = "LAST_NAME"
        private const val USER_TYPE_ID = "USER_TYPE_ID"
        private const val USER_IMAGE = "USER_IMAGE"
        private const val PRODUCT_MAIN_IMAGE = "PRODUCT_MAIN_IMAGE"

    }
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var userId = preferences.getString(USER_ID, "null")
        set(value) = preferences.edit().putString(USER_ID,value).apply()

    var firstName = preferences.getString(FIRST_NAME, "null")
        set(value) = preferences.edit().putString(FIRST_NAME,value).apply()



    var lastName = preferences.getString(LAST_NAME, "null")
        set(value) = preferences.edit().putString(LAST_NAME,value).apply()


    var userTypeId = preferences.getString(USER_TYPE_ID, "null")
        set(value) = preferences.edit().putString(USER_TYPE_ID,value).apply()

    var userImage = preferences.getString(USER_IMAGE, "R.drawable.tesla")
        set(value) = preferences.edit().putString(USER_IMAGE,value).apply()

}


