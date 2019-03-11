package com.example.osrs.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.osrs.fragments.LoginFragment
import com.example.osrs.fragments.ProductsFragment

class MyPagerAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when (position) {

            0 -> ProductsFragment()
            else -> {
                return LoginFragment()
            }
        } // end when
    } // end getItem

    override fun getCount(): Int {
        return 2
    } // end getCount fun

    override fun getPageTitle(position: Int): CharSequence {

        return when (position) {
            0 -> "Products"
            else -> {
                return "Login"
            }
        } // end when

    } // end getPageTitle fun

} // end MyPagerAdapter