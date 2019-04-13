package com.example.osrs.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.osrs.R
import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.osrs.adapters.ChatListAdapter
import com.example.osrs.services.BackendVolley
import com.example.osrs.services.ServiceVolley
import kotlinx.android.synthetic.main.activity_chat_list.*
import org.json.JSONArray
import com.example.osrs.Prefs
import kotlinx.android.synthetic.main.activity_product_details.*

class ChatListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)


        setSupportActionBar(toolbar_chat)
        val actionBar = supportActionBar
        actionBar!!.title = "Chat"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)


        getPossibleChannels(this)
    }




    fun getPossibleChannels(context: Context) {
//
        val Prefs = Prefs(this)
        val titles : ArrayList<String> = arrayListOf()
        val productIds:ArrayList<Int> = arrayListOf()


        val getAllProductsBasePath = "http://18.219.85.157/vendors/"+Prefs.userId.toString().toInt()+"/multi_bids"
        val TAG = ServiceVolley::class.java.simpleName

        var myListAdapter: ChatListAdapter

        val jsonObjReq =
            object : JsonArrayRequest(
                Request.Method.GET,
                getAllProductsBasePath,
                null,
                Response.Listener<JSONArray> { response ->


                    for (i in 0 until  response.length() ){
                        val jsonObject = response.getJSONObject(i)
                        if (jsonObject.has("product_id")){
                            productIds.add(i,jsonObject["product_id"].toString().toInt())
                            titles.add(i,"New Possible Conversation")
                        } // end if
                    } // end for

                    myListAdapter = ChatListAdapter(
                        context,
                        titles,
                        productIds
                    )
                    possible_chats_lv.adapter = myListAdapter

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

    } // end getAllProducts


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    } // end onSupportNavigateUp


}
