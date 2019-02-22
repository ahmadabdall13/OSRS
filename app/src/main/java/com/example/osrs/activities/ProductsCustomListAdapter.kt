package com.example.osrs.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.support.v4.content.ContextCompat.startActivity
import com.example.osrs.R
import com.facebook.FacebookSdk.getApplicationContext


class ProductsCustomListAdapter(
    context: Context?,
    private val title: Array<String>,
    private val description: Array<String>,
    private val imgid: Array<Int>
) : ArrayAdapter<String>(context, R.layout.products_custom_list, title) {

    @SuppressLint("ViewHolder", "InflateParams", "ServiceCast")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.products_custom_list, null, true)
        val titleText = rowView.findViewById(R.id.title) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView
        val subtitleText = rowView.findViewById(R.id.description) as TextView
        titleText.text = title[position]
        imageView.setImageResource(imgid[position])
        subtitleText.text = description[position]
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