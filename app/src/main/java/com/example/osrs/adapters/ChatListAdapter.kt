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
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.osrs.R
import com.example.osrs.activities.ConversationActivity
import com.example.osrs.activities.ProductDetailsActivity
import com.facebook.FacebookSdk.getApplicationContext
import org.json.JSONObject
import com.example.osrs.Prefs
import com.example.osrs.services.BackendVolley
import com.example.osrs.services.ServiceVolley
import kotlinx.android.synthetic.main.activity_pre_login.*
import org.json.JSONArray


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


        rowView.setOnClickListener {
           getUsersAndCreateChat(productIds[position],context)
        }


        return rowView
    }

    fun getUsersAndCreateChat(productId:Int , context:Context){
        val Prefs = Prefs(context)
        val vendorId=Prefs.userId.toString().toInt()

        val basePath = "http://18.219.85.157/"
        val path = "http://18.219.85.157/products/${productId}/users"
        val TAG = ServiceVolley::class.java.simpleName
        var users:ArrayList<Int> = arrayListOf()

        val jsonObjReq =
            object : JsonArrayRequest(
                Request.Method.GET,
                path,
                null,
                Response.Listener<JSONArray> { response ->


                    for (i in 0 until  response.length() ){
                        val jsonObject = response.getJSONObject(i)
                        if (jsonObject.has("customer_id")){
                            users.add(i,jsonObject["customer_id"].toString().toInt())
                        } // end if
                        if(i == response.length()-1)
                            createChannel(users,vendorId,productId,context)

                    } // end for

                },
                Response.ErrorListener { error ->
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json"
                    return headers
                }
            }

        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)


    }


    fun createChannel(users:ArrayList<Int> ,vendorId:Int,productId:Int,context:Context){
//        Toast.makeText(context,users.toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(context,vendorId.toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(context,productId.toString(), Toast.LENGTH_SHORT).show()

        val basePath = "http://18.219.85.157/"

        val productOj = JSONObject()
        productOj.put("users", JSONArray(users))
        productOj.put("vendor_id", vendorId)
        productOj.put("product_id", productId)


        val jsonObjReq = object : JsonObjectRequest(
            Request.Method.POST, basePath + "channels", productOj,
            Response.Listener<JSONObject> { response ->


                    val channelId = response["id"]

                if (!channelId.equals("")) {

                    Toast.makeText(
                        context, "The Channel has been created ${channelId}, thx "
                        , Toast.LENGTH_LONG
                    ).show()

                    updateProduct(productId, channelId.toString().toInt(), context)
                }


            },
            Response.ErrorListener { error ->
                VolleyLog.e("TAG", "/post request fail! Error: ${error.message}")
                Toast.makeText(context, "Error : Sing The Fuck Up ${error.message}", Toast.LENGTH_LONG).show()


            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }

        BackendVolley.instance?.addToRequestQueue(jsonObjReq, "TAG")


    }



    fun updateProduct(productId:Int,channelId:Int,context:Context){

        val basePath = "http://18.219.85.157/"

        val jsonObjReq = object : JsonObjectRequest(
            Request.Method.PUT, basePath + "products/${productId}/updated_at", null,
            Response.Listener<JSONObject> { response ->


                context.applicationContext.startActivity(
                    Intent(
                        context.applicationContext,
                        ConversationActivity::class.java
                    ).setFlags(FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("channel_id", channelId.toString())
                )

                Toast.makeText(
                    context, ", has been updated , thank you ...  ${response} "
                    , Toast.LENGTH_LONG
                ).show()
            },
            Response.ErrorListener { error ->
                VolleyLog.e("TA", "/post request : ${error.message}")
                Toast.makeText(context, "Error :  ${error.message}", Toast.LENGTH_LONG).show()


            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }

        BackendVolley.instance?.addToRequestQueue(jsonObjReq, "TAG")


    }
}