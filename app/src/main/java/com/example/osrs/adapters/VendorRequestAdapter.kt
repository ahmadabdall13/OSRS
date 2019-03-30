package com.example.osrs.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.osrs.R
import com.example.osrs.activities.ProductDetailsActivity

class VendorRequestAdapter(
    context: Context?,
    private val carBrandTextA: ArrayList<String>,
    private val carIds: ArrayList<Int>,
    private val imgid: ArrayList<Int>,
    private val usersNames: ArrayList<String>,
    private val carsPrices: ArrayList<Double>,
    private val offers: ArrayList<String>

) : ArrayAdapter<String>(context, R.layout.recieved_request_custom_list, carBrandTextA) {

    @SuppressLint("ViewHolder", "InflateParams", "ServiceCast")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.recieved_request_custom_list, null, true)

        val productName = rowView.findViewById(R.id.product_name_tv) as TextView
        val productPrice = rowView.findViewById(R.id.product_price_tv) as TextView
        val userName = rowView.findViewById(R.id.user_full_name_tv) as TextView
        val userImage = rowView.findViewById(R.id.user_image_iv) as ImageView
        val offersTv = rowView.findViewById(R.id.request_id_tv) as TextView



        productName.text = carBrandTextA[position]
        offersTv.text=offers[position]
        productPrice.text = carsPrices[position].toString()+" "+ "JOD"
        userName.text = usersNames[position]

        userImage.setImageResource(imgid[position])
//        imageView.setOnClickListener {
//            context.applicationContext.startActivity(
//                Intent(
//                    context.applicationContext,
//                    ProductDetailsActivity::class.java
//                ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    .putExtra("id",carIds[position].toString())
//                    .putExtra("adapterType",adapterType[position])
//                    .putExtra("brand",carBrandTextA[position])
//                    .putExtra("model",carModelTextA[position])
//                    .putExtra("mileage",mileageTextA[position].toString())
//                    .putExtra("transmission",transmissionTextA[position])
//                    .putExtra("price",carPriceTextA[position].toString())
//                    .putExtra("status",offerStatusTextA[position])
//            )
//        }
        return rowView
    }

}