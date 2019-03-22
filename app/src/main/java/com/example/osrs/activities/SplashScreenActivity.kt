package com.example.osrs.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import com.example.osrs.R
import com.example.osrs.Prefs

class SplashScreenActivity : Activity() {


    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000

    private var pStatus = 0
    private val handler = Handler()

    private val mRunnable: Runnable = Runnable {

        if (!isFinishing) {

            val intent = Intent(applicationContext, PreLoginActivity::class.java)
//            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        } // end if

    } // end mRunnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // this two lines for hiding the bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_splash_screen)

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

        val res = resources
        val drawable = res.getDrawable(R.drawable.circular)
        val mProgress = findViewById<View>(R.id.circularProgressbar) as ProgressBar
        mProgress.progress = 0   // Main Progress
        mProgress.secondaryProgress = 100 // Secondary Progress
        mProgress.max = 100 // Maximum Progress
        mProgress.progressDrawable = drawable

        Thread(Runnable {
            // TODO Auto-generated method stub
            while (pStatus < 100) {
                pStatus += 1

                handler.post {
                    // TODO Auto-generated method stub
                    mProgress.progress = pStatus

                }
                try {
                    // Sleep for 200 milliseconds.
                    // Just to display the progress slowly

                    val Prefs = Prefs(this)

//                    val token = Prefs.deviceToken

//                    Log.v("TAG","My token is: $token")
//                    Toast.makeText(applicationContext,"===> ${token}", Toast.LENGTH_LONG).show()
//                    Log.d("Batool FUCK HER PUSSY222", token)


                    Thread.sleep(16) //thread will take approx 3 seconds to finish


                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }).start()

    } // end onCreate

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    } // end onDestroy


} // end SplashScreenActivity
