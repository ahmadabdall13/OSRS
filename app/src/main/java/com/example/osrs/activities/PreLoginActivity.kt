package com.example.osrs.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import android.widget.Toast
import com.example.osrs.R
import com.example.osrs.activities.fragments.MyPagerAdapter
import kotlinx.android.synthetic.main.activity_pre_login.*
import kotlinx.android.synthetic.main.fragment_products.*


class PreLoginActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_login)

        // these three lines for the tabs
        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        viewpager_main.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(viewpager_main)



} // end onCreate
} // end PreLoginActivity




