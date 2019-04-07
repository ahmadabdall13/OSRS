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
import com.example.osrs.activities.ConversationActivity


class ActiveChatAdapter(
    context: Context?,
    private val titles: ArrayList<String>,
    private val ids: ArrayList<Int>,
    private val productIds: ArrayList<Int>





) : ArrayAdapter<String>(context, R.layout.active_chat_custom, titles) {

    @SuppressLint("ViewHolder", "InflateParams", "ServiceCast")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.active_chat_custom, null, true)

        val productIdText = rowView.findViewById(R.id.active_productId_tv) as TextView
        val chatTitleText = rowView.findViewById(R.id.active_chat_title_tv) as TextView

        productIdText.text = "#productId= "+productIds[position].toString()
        chatTitleText.text = titles[position].toString()


        rowView.setOnClickListener {
            context.applicationContext.startActivity(
                Intent(
                    context.applicationContext,
                    ConversationActivity::class.java
                ).setFlags(FLAG_ACTIVITY_NEW_TASK)
            )
        }

        return rowView
    }

}