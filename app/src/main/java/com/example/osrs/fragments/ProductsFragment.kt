package com.example.osrs.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.osrs.Adapters.ProductsCustomListAdapter
import kotlinx.android.synthetic.main.fragment_products.*
import com.example.osrs.R


class ProductsFragment  : Fragment() {
    private val CarBrand = arrayOf("Audi","BMW")
    private val CarModle = arrayOf(
        "A7",
        "Tiger"
    )

    private val imageId = arrayOf(
        R.drawable.audi,
        R.drawable.audi
    )

    private val MileAge:Array<Double> = arrayOf(
        13.0,0.0
    )

    private val Trans = arrayOf(
        "Auto",
        "Manual"
    )

    private val CarPrice:Array<Double> = arrayOf(
        700_000.213,13_22_13.22
    )

    private val OfferStatus:Array<String> = arrayOf(
        "Pending","Declined"
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    } // end onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
                val myListAdapter = ProductsCustomListAdapter(
                    context,
                    CarBrand,
                    CarModle,
                    MileAge,
                    Trans,
                    CarPrice,
                    imageId,
                    OfferStatus
                )
        productsLV.adapter = myListAdapter



    } // end onViewCreated

} // end ProductsFragment