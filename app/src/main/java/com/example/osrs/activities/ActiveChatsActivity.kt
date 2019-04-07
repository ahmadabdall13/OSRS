package com.example.osrs.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.osrs.Prefs
import com.example.osrs.R
import kotlinx.android.synthetic.main.activity_active_chats.*
import kotlinx.android.synthetic.main.activity_chat_list.*
import com.example.osrs.adapters.ActiveChatAdapter
import com.example.osrs.adapters.ChatListAdapter
import com.example.osrs.services.BackendVolley
import com.example.osrs.services.ServiceVolley
import org.json.JSONArray


class ActiveChatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_active_chats)


        setSupportActionBar(toolbar_active_chat)
        val actionBar = supportActionBar
        actionBar?.title = "Active Chat"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        getActiveChannels(this)


    }





    fun getActiveChannels(context: Context) {
//
        val Prefs = Prefs(this)
        var titles : ArrayList<String> = arrayListOf()
        var productIds:ArrayList<Int> = arrayListOf()
        var channelIds:ArrayList<Int> = arrayListOf()

//        http://18.219.85.157/vendors/17/channels

        val getChannelVendors = "http://18.219.85.157/vendors/"+Prefs.userId.toString().toInt()+"/channels"
        val getChannelUsers = "http://18.219.85.157/users/"+Prefs.userId.toString().toInt()+"/channels"
        if(Prefs.userTypeId.equals("1")){

            val TAG = ServiceVolley::class.java.simpleName

            var myListAdapter : ActiveChatAdapter = ActiveChatAdapter(
                context,
                titles,
                channelIds,
                productIds
            )

            val jsonObjReq =
                object : JsonArrayRequest(
                    Request.Method.GET,
                    getChannelUsers,
                    null,
                    Response.Listener<JSONArray> { response ->


                        for (i in 0 until  response.length() ){
                            val jsonObject = response.getJSONObject(i)
                            if (jsonObject.has("channel_id")){
                                productIds.add(i,1)
                                titles.add(i,"Active Conversation")
                                productIds.add(i,jsonObject["channel_id"].toString().toInt())
                            } // end if
                        } // end for

                        myListAdapter = ActiveChatAdapter(
                            context,
                            titles,
                            channelIds,
                            productIds
                        )
                        active_chats_lv.adapter = myListAdapter

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
        else {


            val TAG = ServiceVolley::class.java.simpleName

            var myListAdapter : ActiveChatAdapter = ActiveChatAdapter(
                context,
                titles,
                channelIds,
                productIds
            )

            val jsonObjReq =
                object : JsonArrayRequest(
                    Request.Method.GET,
                    getChannelVendors,
                    null,
                    Response.Listener<JSONArray> { response ->


                        for (i in 0 until  response.length() ){
                            val jsonObject = response.getJSONObject(i)
                            if (jsonObject.has("id")){
                                productIds.add(i,jsonObject["product_id"].toString().toInt())
                                titles.add(i,"Active Conversation")
                                productIds.add(i,jsonObject["id"].toString().toInt())
                            } // end if
                        } // end for

                        myListAdapter = ActiveChatAdapter(
                            context,
                            titles,
                            channelIds,
                            productIds
                        )
                        active_chats_lv.adapter = myListAdapter

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


    } // end getAllProducts

}
