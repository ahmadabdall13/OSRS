package com.example.osrs.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.osrs.R
import com.example.osrs.services.ServiceVolley
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.io.IOException
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.media.MediaRecorder.VideoSource.CAMERA


class SignUpActivity : AppCompatActivity() {


    private var filePath : Uri? = null
    private var storage : FirebaseStorage? = null
    private var storageReference : StorageReference? = null
    private val PICK_IMAGE_REQUEST = 1234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        val intent: Intent = intent
        val socialId = intent.getStringExtra("social_id")



        setSupportActionBar(sp_toolbar)
        val actionBar = supportActionBar
        actionBar!!.title= "Sign Up"


        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        //init Firebase
        FirebaseApp.initializeApp(this)
        storage= FirebaseStorage.getInstance()
        storageReference=storage!!.reference


        chooseProfilePictureBTN.setOnClickListener {

            // this code from Zaid's guy
            val intent1 :Intent = getIntent()
            intent1.type = "image/*"
            intent1.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent1,"SELECT PICTURE"),PICK_IMAGE_REQUEST)

//            showPictureDialog()

        } // end chooseProfilePictureBTN.setOnClickListener

        signUpBTN.setOnClickListener {

            val spinnerValue = userTypeSpinner.selectedItem.toString()

            val userTypeID = if (spinnerValue.toLowerCase() == "customer")
                1
            else
                2



            if (!checkNull()){
                Toast.makeText(applicationContext,"please try again ! "
                    , Toast.LENGTH_LONG).show()
            } // end if
            else {
                ServiceVolley().singUp(
                    signUpEmailAddressET.text.toString(), signUpPasswordET.text.toString()
                    , firstNameET.text.toString(), lastNameET.text.toString(), mobileNumberET.text.toString(),userTypeID,socialId, this
                )
            } // end else


        } // end signUpBTN.setOnClickListener

    } // end onCreate

    // my code
    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems,
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    0 -> choosePhotoFromGallery()
                    1 -> takePhotoFromCamera()
                }
            })
        pictureDialog.show()
    } // end showPictureDialog

    private fun takePhotoFromCamera() {

        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    } // end takePhotoFromCamera

    private fun choosePhotoFromGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
//        startActivityForResult(galleryIntent,GALLERY)
    } // end choosePhotoFromGallery

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    } // end onSupportNavigateUp

    private fun checkNull():Boolean{
    if(signUpEmailAddressET.text.toString()==""){
        return false
    }
    if(firstNameET.text.toString()==""){
        return false
    }

    if(lastNameET.text.toString()==""){
        return false
    }

    if(mobileNumberET.text.toString()==""){
        return false
    }
    if(signUpPasswordET.text.toString()==""){
        return false
    }

    if(confirmPasswordET.text.toString()==""){
        return false
    }
    if(confirmPasswordET.text.toString()!=signUpPasswordET.text.toString() ){
        return false
    }
    return true
} // end checkNull

    //zaid code
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST &&
                resultCode == Activity.RESULT_OK &&
                data != null && data.data != null ){
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filePath)
                profilePictureIMG!!.setImageBitmap(bitmap)
            } // end try
            catch (e:IOException){
                e.printStackTrace()
            } // end catch

        } // end if

    } // end onActivityResult

} // end SignUpActivity
