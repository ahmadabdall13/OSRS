package com.example.osrs.activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.Gravity
import com.example.osrs.R
import kotlinx.android.synthetic.main.activity_product_details.*
import android.view.LayoutInflater
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.transition.Slide
import android.widget.*
import com.facebook.FacebookSdk
import com.squareup.picasso.Picasso


class ProductDetailsActivity : AppCompatActivity() {




    val productSubImages : ArrayList<String> = intent.getStringArrayListExtra("productSubImages")
    val productMainImage = intent.getStringExtra("productMainImage")


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        Picasso
            .with(this) // give it the context
            .load(productMainImage) // load the image
            .into(productMainImageIMG)

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
                Toast.makeText(applicationContext,"Fuck You",Toast.LENGTH_SHORT).show()
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

    } // end onCreate
} // end ProductDetailsActivity
