package com.example.osrs.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.osrs.R
import com.example.osrs.activities.ProductDetailsActivity
import com.facebook.FacebookSdk.getApplicationContext


class ProductsCustomListAdapter(
    context: Context?,
    private val carBrandTextA: Array<String>,
    private val carModelTextA: Array<String>,
    private val mileageTextA: Array<Double>,
    private val transmissionTextA: Array<String>,
    private val carPriceTextA: Array<Double>,
    private val imgid: Array<Int>,
    private val offerStatusTextA: Array<String>


) : ArrayAdapter<String>(context, R.layout.products_custom_list, carBrandTextA) {

    @SuppressLint("ViewHolder", "InflateParams", "ServiceCast")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.products_custom_list, null, true)

        val carBrandText = rowView.findViewById(R.id.carBrandTv) as TextView
        val carModelText = rowView.findViewById(R.id.carModelTv) as TextView
        val mileageText = rowView.findViewById(R.id.carMileageTv) as TextView
        val transmissionText = rowView.findViewById(R.id.transmissionTv) as TextView
        val carPriceText = rowView.findViewById(R.id.carPriceTv) as TextView
        val offerStatusText = rowView.findViewById(R.id.offerStatusTv) as TextView

        val imageView = rowView.findViewById(R.id.mainCarImgView) as ImageView


        carBrandText.text = carBrandTextA[position]
        carModelText.text = carModelTextA[position]
        mileageText.text = mileageTextA[position].toString()
        transmissionText.text = transmissionTextA[position]
        carPriceText.text = carPriceTextA[position].toString()
        offerStatusText.text = offerStatusTextA[position]

        imageView.setImageResource(imgid[position])
        imageView.setOnClickListener {
            context.applicationContext.startActivity(
                Intent(
                    getApplicationContext(),
                    ProductDetailsActivity::class.java
                ).setFlags(FLAG_ACTIVITY_NEW_TASK)
            )
        }
        return rowView
    }

}