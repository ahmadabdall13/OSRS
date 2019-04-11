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


class MsgsAdapter(
    context: Context?,
    private val msgs: ArrayList<String>,
    private val userIds: ArrayList<Int>,
    private val ids: ArrayList<Int>

) : ArrayAdapter<String>(context, R.layout.conversation_item, msgs) {

    @SuppressLint("ViewHolder", "InflateParams", "ServiceCast")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.conversation_item, null, true)

        val user_id_msg = rowView.findViewById(R.id.user_id_msg) as TextView
        val user_msg = rowView.findViewById(R.id.user_msg) as TextView

        user_id_msg.text = "userId: "+userIds[position].toString()
        user_msg.text = msgs[position].toString()

        return rowView
    }

}