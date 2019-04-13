package com.example.osrs.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.osrs.Prefs
import com.example.osrs.R
import kotlinx.android.synthetic.main.activity_conversation.*
import com.example.osrs.adapters.MsgsAdapter
import com.example.osrs.services.BackendVolley
import com.example.osrs.services.ServiceVolley
import org.json.JSONArray
import android.content.Context
import android.content.Intent

class ConversationActivity : AppCompatActivity() {

    var channel_id : Any? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation)


        val Prefs = Prefs(this)
        val intent1: Intent = intent
        channel_id = intent1.getStringExtra("channel_id")


        setSupportActionBar(toolbar_conversation)
        val actionBar = supportActionBar
        actionBar!!.title = "Conversation"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)


//        {
//            msg:””,
//            user_id:1,
//            channel_id:22
//        }
        getConversations(this)

        sendMsgIv.setOnClickListener{

            ServiceVolley().sendMsg(
                editTextConversation.text.toString(), Prefs.userId.toString().toInt(),channel_id.toString().toInt(), this
            )
            getConversations(this)

        }

    }


    private fun getConversations(context:Context){
        //
        val Prefs = Prefs(this)
        val msgs : ArrayList<String> = arrayListOf()
        val userIds:ArrayList<Int> = arrayListOf()
        val ids:ArrayList<Int> = arrayListOf()


        val getConversations = "http://18.219.85.157/channels/$channel_id/chats"

            val TAG = ServiceVolley::class.java.simpleName

        var myListAdapter: MsgsAdapter

        val jsonObjReq =
                object : JsonArrayRequest(
                    Request.Method.GET,
                    getConversations,
                    null,
                    Response.Listener<JSONArray> { response ->

                        for (i in 0 until  response.length() ){
                            val jsonObject = response.getJSONObject(i)
                            if (jsonObject.has("channel_id")){
                                msgs.add(i,jsonObject["msg"].toString())
                                userIds.add(i,jsonObject["user_id"].toString().toInt())
                                ids.add(i,jsonObject["channel_id"].toString().toInt())
                            } // end if
                        } // end for

                        myListAdapter = MsgsAdapter(
                            context,
                            msgs,
                            userIds,
                            ids
                        )
                        conversationsListView1.adapter = myListAdapter

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


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    } // end onSupportNavigateUp


}
