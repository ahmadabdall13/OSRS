package com.example.osrs.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.osrs.R
import com.example.osrs.activities.fragments.MyPagerAdapter
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_pre_login.*
import kotlinx.android.synthetic.main.fragment_products.*


class PreLoginActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        when (menuItem.itemId) {
            R.id.account -> {
                Toast.makeText(this, "Account bitch", Toast.LENGTH_SHORT).show()
            }
            R.id.settings -> {
                Toast.makeText(this, "Fuck Your Settings", Toast.LENGTH_SHORT).show()
            }
            R.id.mycart -> {
                Toast.makeText(this, "And Fuck Your Cart", Toast.LENGTH_SHORT).show()
            }

        }
        navigationDrawer.closeDrawer(GravityCompat.START)

        return true
    }
    override fun onBackPressed() {
        if (navigationDrawer.isDrawerOpen(GravityCompat.START)) {
            navigationDrawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    companion object {
    fun newInstance(): PreLoginActivity = PreLoginActivity()
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_login)

        // these three lines for the tabs
        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        viewpager_main.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(viewpager_main)



} // end onCreate
} // end PreLoginActivity




