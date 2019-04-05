package com.example.osrs.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.Gravity
import com.example.osrs.R
import kotlinx.android.synthetic.main.activity_product_details.*
import android.view.LayoutInflater
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.Toolbar
import android.transition.Slide
import android.view.View
import android.widget.*
import com.example.osrs.Prefs
import com.example.osrs.services.ServiceVolley
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_pre_login.*
import kotlinx.android.synthetic.main.activity_product_details.view.*
import org.json.JSONObject
import android.net.Uri
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.util.Log
import android.provider.ContactsContract
import android.provider.MediaStore
import io.vrinda.kotlinpermissions.PermissionCallBack
import io.vrinda.kotlinpermissions.PermissionsActivity
import android.Manifest
import android.annotation.SuppressLint
import android.support.v4.app.ActivityCompat

class ProductDetailsActivity : AppCompatActivity() {

//    .putExtra("id",carIds[position])
//    .putExtra("brand",carBrandTextA[position])
//    .putExtra("model",carModelTextA[position])
//    .putExtra("mileage",mileageTextA[position])
//    .putExtra("transmission",transmissionTextA[position])
//    .putExtra("price",carPriceTextA[position])
//    .putExtra("status",offerStatusTextA[position])


//    private var id = 0;

    var mobileNumber : Any? = null
    var CAMERAMODE:Int=1
    var GALLERYMODE:Int=2
    var CALLMODE:Int=3
    var CONATACTMODE:Int=4
    var boolean_camera:Boolean=false
    var boolean_gallery:Boolean=false
    var boolean_call:Boolean=false
    var boolean_contact:Boolean=false


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val Prefs = Prefs(this)
        var Intent1: Intent
        Intent1= getIntent()
        val productId = Intent1.getStringExtra("id")
        val adapterType = Intent1.getStringExtra("adapterType")
        val brand = Intent1.getStringExtra("brand")
        val model = Intent1.getStringExtra("model")
        val mileage = Intent1.getStringExtra("mileage")+" "+"mile"
        val transmission = Intent1.getStringExtra("transmission")
        val price = Intent1.getStringExtra("price")+" "+"JOD"
        val status = Intent1.getStringExtra("status")
        val productType=Intent1.getStringExtra("productType").toString()

        val vendor = Intent1.getStringExtra("vendor")
        val json = JSONObject(vendor)
        val email_address= json["email_address"].toString()
        val first_name= json["first_name"].toString()
        val last_name= json["last_name"].toString()
        mobileNumber=json["mobile_number"].toString()

        tv_vendor_name.text=first_name +" "+ last_name
        Toast.makeText(applicationContext,vendor,Toast.LENGTH_SHORT).show()

        tv_brand_details!!.text=brand
        tv_model_details!!.text=model
        tv_price_details!!.text=price
        tv_transmission_details!!.text=transmission
        tv_mileage_details!!.text=mileage


        setSupportActionBar(toolbar_product_details)
        val actionBar = supportActionBar
        actionBar?.title = "Details"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)


        someOfValidations()

        btn_buy1.setOnClickListener {

            ServiceVolley().createUserRequest(
                productId.toString().toInt(),  Prefs.userId.toString().toInt() ,  1 ,applicationContext)


            val intent = Intent(applicationContext, PreLoginActivity::class.java)
            startActivity(intent)

        } // end Buy





        call_vendor.setOnClickListener{
            Toast.makeText(applicationContext,mobileNumber.toString(),Toast.LENGTH_SHORT).show()

            checkPermission()

        }



        subImage1.setOnClickListener{

            val inflater:LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.product_image_popup,null)

            // Initialize a new instance of popup window
            val popupWindow = PopupWindow(
                view, // Custom view to show in popup window
                LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                LinearLayout.LayoutParams.WRAP_CONTENT // Window height
            )
            popupWindow.isOutsideTouchable = true
            popupWindow.isFocusable = true


            // Set an elevation for the popup window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.elevation = 10.0F
            }


            // If API level 23 or higher then execute the code
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                // Create a new slide animation for popup window enter transition
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.TOP
                popupWindow.enterTransition = slideIn

                // Slide animation for popup window exit transition
                val slideOut = Slide()
                slideOut.slideEdge = Gravity.END
                popupWindow.exitTransition = slideOut

            }



            // Set a dismiss listener for popup window
            popupWindow.setOnDismissListener {
//                Toast.makeText(applicationContext,"haha",Toast.LENGTH_SHORT).show()
            }


            // Finally, show the popup window on app
            TransitionManager.beginDelayedTransition(test)
            popupWindow.showAtLocation(
                test, // Location to display popup window
                Gravity.CENTER, // Exact position of layout to display popup
                0, // X offset
                0 // Y offset
            )
        }



        btn_delete_product.setOnClickListener{
            ServiceVolley().deleteProduct(
                productId.toString().toInt(),applicationContext)
            val intent = Intent(applicationContext, PreLoginActivity::class.java)
            startActivity(intent)
        }


        fav_tv.setOnClickListener{
            Toast.makeText(applicationContext,Prefs.userId.toString(),Toast.LENGTH_SHORT).show()
            Toast.makeText(applicationContext,productType,Toast.LENGTH_SHORT).show()

        }
        favourite_iv.setOnClickListener{
//            Toast.makeText(applicationContext,Prefs.userId.toString(),Toast.LENGTH_SHORT).show()
//            Toast.makeText(applicationContext,productType,Toast.LENGTH_SHORT).show()
            ServiceVolley().likeProductType(
                productType.toString().toInt(),  Prefs.userId.toString().toInt() ,applicationContext)
        }



    } // end onCreate

   fun someOfValidations(){

       val Prefs = Prefs(this)
       var Intent1: Intent
       Intent1= getIntent()
       val productId = Intent1.getStringExtra("id")
       val adapterType = Intent1.getStringExtra("adapterType")

        if(Prefs.userTypeId.equals("1")){
            btn_delete_product.visibility= View.INVISIBLE
        }else if (Prefs.userTypeId.equals("2")){
            btn_buy1.visibility= View.INVISIBLE
            fav_tv.visibility=View.INVISIBLE
            favourite_iv.visibility=View.INVISIBLE

        }
        else{
            btn_delete_product.visibility= View.INVISIBLE
            btn_buy1.visibility= View.INVISIBLE
            fav_tv.visibility=View.INVISIBLE
            favourite_iv.visibility=View.INVISIBLE

        }

        if(adapterType.equals("user_request_adapter")){
            btn_delete_product.visibility= View.INVISIBLE
            btn_buy1.visibility= View.INVISIBLE
        }


    }









    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42)
            }
        } else {
            // Permission has already been granted
            callPhone()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 42) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                callPhone()
            } else {
                // permission denied, boo! Disable the
                // functionality
            }
            return
        }
    }
    fun callPhone(){
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobileNumber.toString()))
        startActivity(intent)
    }


} // end ProductDetailsActivity
