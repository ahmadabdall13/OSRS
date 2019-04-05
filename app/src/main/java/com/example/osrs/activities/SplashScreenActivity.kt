package com.example.osrs.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import com.example.osrs.R
import com.example.osrs.Prefs
import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import java.util.ArrayList
import java.util.HashMap

class SplashScreenActivity : Activity() {

    private val TAG = "tag"

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000

    private var pStatus = 0
    private val handler = Handler()

    private val mRunnable: Runnable = Runnable {

        if (!isFinishing) {
            if (checkAndRequestPermissions()) {
                // carry on the normal flow, as the case of  permissions  granted.
                Handler().postDelayed({
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    val intent = Intent(applicationContext, PreLoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }, SPLASH_TIME_OUT.toLong() )
            } // end inner if

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

    private fun checkAndRequestPermissions(): Boolean {
        val camerapermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val writepermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)



        val listPermissionsNeeded = ArrayList<String>()

        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (readPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        Log.d(TAG, "Permission callback called-------")
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {

                val perms = HashMap<String, Int>()
                // Initialize the map with both permissions
                perms[Manifest.permission.CAMERA] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.READ_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                // Fill with actual results from user
                if (grantResults.isNotEmpty()) {
                    for (i in permissions.indices)
                        perms[permissions[i]] = grantResults[i]
                    // Check for both permissions
                    if (perms[Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED
                        && perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                        && perms[Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                        ) {
                        Log.d(TAG, "sms & location services permission granted")
                        // process the normal flow
                        val i = Intent(applicationContext, PreLoginActivity::class.java)
                        startActivity(i)
                        finish()
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ")
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
                        //                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                            || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            ) {
                            showDialogOK("Service Permissions are required for this app",
                                DialogInterface.OnClickListener { dialog, which ->
                                    when (which) {
                                        DialogInterface.BUTTON_POSITIVE -> checkAndRequestPermissions()
                                        DialogInterface.BUTTON_NEGATIVE ->
                                            // proceed with logic by disabling the related features or quit the app.
                                            finish()
                                    }
                                })
                        } else {
                            explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?")
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }//permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                    }
                }
            }
        }

    }

    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", okListener)
            .create()
            .show()
    }

    private fun explain(msg: String) {
        val dialog = android.support.v7.app.AlertDialog.Builder(this)
        dialog.setMessage(msg)
            .setPositiveButton("Yes") { paramDialogInterface, paramInt ->
                //  permissionsclass.requestPermission(type,code);
                startActivity(Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.example.osrs")))
            }
            .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> finish() }
        dialog.show()
    }

    companion object {

        val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
        private val SPLASH_TIME_OUT = 2000
    }

} // end SplashScreenActivity
