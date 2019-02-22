package com.example.osrs.activities

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
import com.facebook.FacebookSdk

class RatingCustomListAdapter (
    context: Context?,
    private val title: Array<String>,
    private val description: Array<String>
) : ArrayAdapter<String>(context, R.layout.rating_custom_list, title) {

    @SuppressLint("ViewHolder", "InflateParams", "ServiceCast")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.rating_custom_list, null, true)

        val rating_title = rowView.findViewById(R.id.rating_title) as TextView
        val actor_name = rowView.findViewById(R.id.actor_name) as TextView

        rating_title.text = title[position]
        actor_name.text = description[position]

        return rowView
    }

}