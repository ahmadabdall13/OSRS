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
import com.example.osrs.R
import com.example.osrs.activities.ProductDetailsActivity
import com.facebook.FacebookSdk.getApplicationContext
import org.json.JSONObject


class ChatListAdapter(
    context: Context?,
    private val titles: ArrayList<String>,
    private val productIds: ArrayList<Int>

) : ArrayAdapter<String>(context, R.layout.chat_custom_list, titles) {

    @SuppressLint("ViewHolder", "InflateParams", "ServiceCast")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.chat_custom_list, null, true)

        val productIdText = rowView.findViewById(R.id.productId_tv) as TextView
        val chatTitleText = rowView.findViewById(R.id.chat_title_tv) as TextView

        productIdText.text = "#productId= "+productIds[position].toString()
        chatTitleText.text = titles[position].toString()

        return rowView
    }

}