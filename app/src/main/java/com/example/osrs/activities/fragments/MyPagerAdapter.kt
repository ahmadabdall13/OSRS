package com.example.osrs.activities.fragments

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MyPagerAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ContactsFragment()
            }
            1 -> ProductsFragment()
            else -> {
                return LoginFragment()
            }
        } // end when
    } // end getItem

    override fun getCount(): Int {
        return 3
    } // end getCount fun

    override fun getPageTitle(position: Int): CharSequence {

        return when (position) {
            0 -> "Contact Us"
            1 -> "Products"
            else -> {
                return "Login"
            }
        } // end when

    } // end getPageTitle fun

} // end MyPagerAdapter