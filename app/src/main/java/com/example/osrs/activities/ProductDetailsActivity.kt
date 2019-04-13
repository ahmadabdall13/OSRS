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
import android.transition.Slide
import android.view.View
import android.widget.*
import com.example.osrs.Prefs
import com.example.osrs.services.ServiceVolley
import org.json.JSONObject
import android.net.Uri
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.Manifest
import android.annotation.SuppressLint
import android.support.v4.app.ActivityCompat
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_image_popup.*


class ProductDetailsActivity : AppCompatActivity() {


    private var mobileNumber : Any? = null




// handle image in popup

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)




        val Prefs = Prefs(this)

        val productSubImages : ArrayList<String> = intent.getStringArrayListExtra("productSubImages")
        val productMainImage = intent.getStringExtra("productMainImage")!!

        val productId = intent.getStringExtra("id")
        val adapterType = intent.getStringExtra("adapterType")
        val brand = intent.getStringExtra("brand")
        val model = intent.getStringExtra("model")
        val mileage = intent.getStringExtra("mileage")+" "+"mile"
        val transmission = intent.getStringExtra("transmission")
        val price = intent.getStringExtra("price")+" "+"JOD"
        val status = intent.getStringExtra("status")
        val productType=intent.getStringExtra("productType").toString()

        val vendor = intent.getStringExtra("vendor")
        val json = JSONObject(vendor)
        val email_address= json["email_address"].toString()
        val firstName= json["first_name"].toString()
        val lastName= json["last_name"].toString()
        mobileNumber=json["mobile_number"].toString()

        tv_vendor_name.text= "$firstName $lastName"
        //Toast.makeText(applicationContext,vendor,Toast.LENGTH_SHORT).show()

        tv_brand_details!!.text=brand
        tv_model_details!!.text=model
        tv_price_details!!.text=price
        tv_transmission_details!!.text=transmission
        tv_mileage_details!!.text=mileage


        setSupportActionBar(toolbar_product_details)
        val actionBar = supportActionBar
        actionBar!!.title = "Details"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)


        someOfValidations()

        btn_buy1.setOnClickListener {

            ServiceVolley().createUserRequest(
                productId.toString().toInt(),  Prefs.userId.toString().toInt() ,  1 ,applicationContext)


            val intent = Intent(applicationContext, PreLoginActivity::class.java)
            startActivity(intent)

        } // end Buy





        call_vendor.setOnClickListener{
           // Toast.makeText(applicationContext,mobileNumber.toString(),Toast.LENGTH_SHORT).show()

            checkPermission()

        }






        btn_delete_product.setOnClickListener{
            ServiceVolley().deleteProduct(
                productId.toString().toInt(),applicationContext)
            val intent = Intent(applicationContext, PreLoginActivity::class.java)
            startActivity(intent)
        }


        fav_tv.setOnClickListener{
            //Toast.makeText(applicationContext,Prefs.userId.toString(),Toast.LENGTH_SHORT).show()
            //Toast.makeText(applicationContext,productType,Toast.LENGTH_SHORT).show()

        }
        favourite_iv.setOnClickListener{
//            Toast.makeText(applicationContext,Prefs.userId.toString(),Toast.LENGTH_SHORT).show()
//            Toast.makeText(applicationContext,productType,Toast.LENGTH_SHORT).show()
            ServiceVolley().likeProductType(
                productType.toInt(),  Prefs.userId.toString().toInt() ,applicationContext)
        }


        if(productMainImage == "null" || productMainImage.isEmpty()){
            Picasso
                .with(this) // give it the context
                .load(R.drawable.tesla) // load the image
                .into(productMainImageIMG)
        } // end if
        else {
            Picasso
                .with(this) // give it the context
                .load(productMainImage) // load the image
                .into(productMainImageIMG)
        }// end else



        for (i in 0 until productSubImages.size){
            when (i) {
                0 -> Picasso
                    .with(this) // give it the context
                    .load(productSubImages[i]) // load the image
                    .into(subImage1)
                1 -> Picasso
                    .with(this) // give it the context
                    .load(productSubImages[i]) // load the image
                    .into(subImage2)
                2 -> Picasso
                    .with(this) // give it the context
                    .load(productSubImages[i]) // load the image
                    .into(subImage3)
                3 -> Picasso
                    .with(this) // give it the context
                    .load(productSubImages[i]) // load the image
                    .into(subImage4)
                4 -> Picasso
                    .with(this) // give it the context
                    .load(productSubImages[i]) // load the image
                    .into(subImage5)
            } // end when

        } // end for


        productMainImageIMG.setOnClickListener{


            val inflater:LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.product_image_popup,null)

            val popupImage : ImageView = view.findViewById(R.id.productImagePopup)

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

            Picasso
                .with(this) // give it the context
                .load(productMainImage) // load the image
                .into(popupImage)
        } // end productMainImageIMG.setOnClickListener


        subImage1.setOnClickListener{

            val inflater:LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.product_image_popup,null)
            val popupImage : ImageView = view.findViewById(R.id.productImagePopup)
            Picasso
                .with(this) // give it the context
                .load(productSubImages[0]) // load the image
                .into(popupImage)
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
        } // end subImage1.setOnClickListener


        subImage2.setOnClickListener{



            Picasso
                .with(this) // give it the context
                .load(productSubImages[1]) // load the image
                .into(productImagePopup)

            val inflater:LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.product_image_popup,null)

            val popupImage : ImageView = view.findViewById(R.id.productImagePopup)
            Picasso
                .with(this) // give it the context
                .load(productSubImages[1]) // load the image
                .into(popupImage)

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
        } // end subImage2.setOnClickListener


        subImage3.setOnClickListener{

            val inflater:LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.product_image_popup,null)
            val popupImage : ImageView = view.findViewById(R.id.productImagePopup)
            Picasso
                .with(this) // give it the context
                .load(productSubImages[2]) // load the image
                .into(popupImage)
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
        } // end subImage3.setOnClickListener


        subImage4.setOnClickListener{

            val inflater:LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.product_image_popup,null)

            val popupImage : ImageView = view.findViewById(R.id.productImagePopup)
            Picasso
                .with(this) // give it the context
                .load(productSubImages[3]) // load the image
                .into(popupImage)
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
        } // end subImage4.setOnClickListener


        subImage5.setOnClickListener{

            val inflater:LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.product_image_popup,null)

            val popupImage : ImageView = view.findViewById(R.id.productImagePopup)
            Picasso
                .with(this) // give it the context
                .load(productSubImages[4]) // load the image
                .into(popupImage)

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
        } // end subImage1.setOnClickListener



        when (productSubImages.size) {
            1 ->  { subImage2.visibility= View.INVISIBLE
                    subImage3.visibility= View.INVISIBLE
                subImage4.visibility= View.INVISIBLE
                subImage5.visibility= View.INVISIBLE
            }
            2 -> {
                subImage3.visibility= View.INVISIBLE
                subImage4.visibility= View.INVISIBLE
                subImage5.visibility= View.INVISIBLE
            }
            3 -> {
                subImage4.visibility= View.INVISIBLE
                subImage5.visibility= View.INVISIBLE
            }
            4 -> {subImage5.visibility= View.INVISIBLE
            }
        } // end when





    } // end onCreate

   private fun someOfValidations(){

       val Prefs = Prefs(this)
       val Intent1: Intent = intent
       val productId = Intent1.getStringExtra("id")
       val adapterType = Intent1.getStringExtra("adapterType")

       when {
           Prefs.userTypeId == "1" -> btn_delete_product.visibility= View.INVISIBLE
           Prefs.userTypeId == "2" -> {
               btn_buy1.visibility= View.INVISIBLE
               fav_tv.visibility=View.INVISIBLE
               favourite_iv.visibility=View.INVISIBLE

           }
           else -> {
               btn_delete_product.visibility= View.INVISIBLE
               btn_buy1.visibility= View.INVISIBLE
               fav_tv.visibility=View.INVISIBLE
               favourite_iv.visibility=View.INVISIBLE

           }
       }

        if(adapterType == "user_request_adapter"){
            btn_delete_product.visibility= View.INVISIBLE
            btn_buy1.visibility= View.INVISIBLE
        }


    }









    private fun checkPermission() {
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
    private fun callPhone(){
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobileNumber.toString()))
        startActivity(intent)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    } // end onSupportNavigateUp



} // end ProductDetailsActivity