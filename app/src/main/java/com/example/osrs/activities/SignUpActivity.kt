package com.example.osrs.activities

import android.app.Activity
import android.app.ProgressDialog
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
import java.util.*
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Environment
import android.support.annotation.RequiresApi
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FileDownloadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.File

class SignUpActivity : AppCompatActivity() {


    private var filePath : Uri? = null
    private var storage : FirebaseStorage? = null
    private var storageReference : StorageReference? = null
    private var mCurrentPhotoPath: String = ""
    private var imageLink = ""


    @RequiresApi(Build.VERSION_CODES.N)
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


//        chooseProfilePictureBTN.setOnClickListener {
//            showPictureDialog()
//        } // end chooseProfilePictureBTN.setOnClickListener

        signUpBTN.setOnClickListener {

            val spinnerValue = userTypeSpinner.selectedItem.toString()

            val userTypeID = if (spinnerValue.toLowerCase() == "customer")
                1
            else
                2


            if(filePath != null){
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading Bitch , Wait")
                progressDialog.show()
                val imageRef = storageReference!!.child("images/"+ UUID.randomUUID().toString() )
                imageRef.putFile(filePath!!)
                    .addOnSuccessListener {
                        val result = it.metadata!!.reference!!.downloadUrl
                        result.addOnSuccessListener {
                            imageLink = it.toString()
                        } // end result.addOnSuccessListener
                        progressDialog.dismiss()
                    } // end imageRef.putFile(filePath!!).addOnSuccessListener
                    .addOnFailureListener{ progressDialog.dismiss() }

            } // end if

            if (!checkNull()){
                Toast.makeText(applicationContext,"please try again ! "
                    , Toast.LENGTH_LONG).show()
            } // end if
            else {
                ServiceVolley().singUp(
                    imageLink,
                    signUpEmailAddressET.text.toString(),
                    signUpPasswordET.text.toString()
                    , firstNameET.text.toString(),
                    lastNameET.text.toString(),
                    mobileNumberET.text.toString(),
                    userTypeID,
                    socialId,
                    this
                )
            } // end else


        } // end signUpBTN.setOnClickListener

    } // end onCreate

    // my code
    @RequiresApi(Build.VERSION_CODES.N)
    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select Photo From Gallery", "Capture Photo From Camera")
        pictureDialog.setItems(pictureDialogItems
        ) { _, which ->
            when (which) {
                0 -> choosePhotoFromGallery()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    } // end showPictureDialog

    @RequiresApi(Build.VERSION_CODES.N)
    private fun takePhotoFromCamera() {


        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file: File = createImageFile()

        val uri: Uri = FileProvider.getUriForFile(
            this,
            "com.example.android.fileprovider",
            file
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri)
        filePath=uri
        startActivityForResult(intent, REQUEST_TAKE_PHOTO)

    } // end takePhotoFromCamera

    private fun choosePhotoFromGallery() {
        val intent2 = Intent(Intent.ACTION_GET_CONTENT)
        intent2.type = "image/*"
        if (intent2.resolveActivity(packageManager) != null) {
            startActivityForResult(intent2, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    } // end choosePhotoFromGallery


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        } else {
            "Date Not Supplied Because SDK < N"
        }
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        } // end apply
    } // end createImageFile


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if ( requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM &&
                resultCode == Activity.RESULT_OK &&
                data != null && data.data != null ){
            filePath = data.data

//            val auxFile = File(filePath.toString())

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filePath)
                profilePictureIMG!!.setImageBitmap(bitmap)
            } // end try
            catch (e:IOException){
                e.printStackTrace()
            } // end catch

        } // end if

        else if (requestCode == REQUEST_TAKE_PHOTO &&
            resultCode == Activity.RESULT_OK ){

//            val auxFile = File(mCurrentPhotoPath)

            val bitmap :Bitmap= BitmapFactory.decodeFile(mCurrentPhotoPath)
            profilePictureIMG.setImageBitmap(bitmap)

        } // end if

    } // end onActivityResult

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



    @RequiresApi(Build.VERSION_CODES.N)
    fun changeProfileImage (v : View){
        showPictureDialog()
    } // end changeProfileImage


    companion object {
    private const val REQUEST_TAKE_PHOTO = 1
    private const val REQUEST_SELECT_IMAGE_IN_ALBUM = 0
}

} // end SignUpActivity
