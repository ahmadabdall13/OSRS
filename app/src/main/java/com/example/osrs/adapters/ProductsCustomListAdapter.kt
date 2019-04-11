package com.example.osrs.adapters

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
import com.example.osrs.Prefs
import com.example.osrs.R
import com.example.osrs.activities.ProductDetailsActivity
import com.facebook.FacebookSdk
import com.facebook.FacebookSdk.getApplicationContext
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pre_login.*
import org.json.JSONObject


class ProductsCustomListAdapter(
    context: Context?,
    private val carBrandTextA: ArrayList<String>,
    private val carIds: ArrayList<Int>,
    private val carModelTextA: ArrayList<String>,
    private val mileageTextA: ArrayList<Double>,
    private val transmissionTextA: ArrayList<String>,
    private val carPriceTextA: ArrayList<Double>,
    private val imgid: ArrayList<String>,
    private val offerStatusTextA: ArrayList<String>,
    private val adapterType: ArrayList<String>,
    private val vendors: ArrayList<JSONObject>,
    private val productTypes: ArrayList<Int>,
    private val subImages :ArrayList<String>

) : ArrayAdapter<String>(context, R.layout.products_custom_list, carBrandTextA) {

    @SuppressLint("ViewHolder", "InflateParams", "ServiceCast", "SetTextI18n")
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
        mileageText.text = mileageTextA[position].toString() + " " + "mile"
        transmissionText.text = transmissionTextA[position]
        carPriceText.text = carPriceTextA[position].toString() + " " + "JOD"
        offerStatusText.text = offerStatusTextA[position]

//        imageView.setImageResource(imgid[position])
        Picasso
            .with(getApplicationContext()) // give it the context
            .load(imgid[position]) // load the image
            .into(imageView)


        imageView.setOnClickListener {
            context.applicationContext.startActivity(
                Intent(
                    context.applicationContext,
                    ProductDetailsActivity::class.java
                ).setFlags(FLAG_ACTIVITY_NEW_TASK)
                    .putExtra("id", carIds[position].toString())
                    .putExtra("adapterType", adapterType[position])
                    .putExtra("brand", carBrandTextA[position])
                    .putExtra("model", carModelTextA[position])
                    .putExtra("mileage", mileageTextA[position].toString())
                    .putExtra("transmission", transmissionTextA[position])
                    .putExtra("price", carPriceTextA[position].toString())
                    .putExtra("status", offerStatusTextA[position])
                    .putExtra("vendor", vendors[position].toString())
                    .putExtra("productType", productTypes[position].toString())
                    .putExtra("productMainImage", imgid[position])
                    .putExtra("productSubImages", subImages)
            )
        }
        return rowView
    }

}