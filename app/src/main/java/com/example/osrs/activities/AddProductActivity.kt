package com.example.osrs.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Toast
import com.example.osrs.Prefs
import com.example.osrs.R
import com.example.osrs.services.ServiceVolley
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class AddProductActivity : AppCompatActivity() {


    private var filePath : Uri? = null
    private var storage : FirebaseStorage? = null
    private var storageReference : StorageReference? = null
    private var mCurrentPhotoPath: String = ""
    private var tag = 0
    private var subImagesArrayList :MutableList<String> = arrayListOf()
    private var mainImageURL = ""


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        val Prefs = Prefs(this)

        var mToolbar: Toolbar = findViewById(R.id.too)
        setSupportActionBar(mToolbar)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Add Product"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        //init Firebase
        FirebaseApp.initializeApp(this)
        storage= FirebaseStorage.getInstance()
        storageReference=storage!!.reference






        addProductBtn.setOnClickListener {
            var CarInfo = hashMapOf<String, Any>()

            if (fieldsValidation() == true) {
                ServiceVolley().addProduct(
                    carBrandEt.text.toString(), carModelEt.text.toString()
                    , yearOfMakeEt.text.toString(), engineCylindersSpinner.selectedItem.toString(),
                    transmissionSpinner.selectedItem.toString(), carPrice.text.toString().toDouble(),
                    carMileage.text.toString().toDouble(), extColor.text.toString(), intColor.text.toString(),
                    productDescriptionEt.text.toString(),
                    productTypeSp.selectedItemId, Prefs.userId.toInt(), this
                )
            } else {
                Toast.makeText(
                    this, " Fill the fields please ", Toast.LENGTH_LONG
                ).show()
            }

//            if (filePath != null) {
//                val progressDialog = ProgressDialog(this)
//                progressDialog.setTitle("Uploading Bitch , Wait")
//                progressDialog.show()
//
//
//                if (tag == 12) {
//
//                    val imageRef = storageReference!!.child("images/" + UUID.randomUUID().toString())
//                    imageRef.putFile(filePath!!)
//                        .addOnSuccessListener {
//                            val result = it.metadata!!.reference!!.downloadUrl
//                            result.addOnSuccessListener {
//
//                                mainImageURL = it.toString()
//
//
//                            } // end result.addOnSuccessListener
//
//                        } // end imageRef.putFile(filePath!!).addOnSuccessListener
//
//                        .addOnFailureListener { }
//                } // end first inner if
//
////             if (tag==13) {
////                } // end second inner if
//            } // end if
        }



        addProductMainImageBtn.setOnClickListener {
            tag=12
            showPictureDialog()
        } // end addProductMainImageBtn.setOnClickListener



        addProductSubImageBtn.setOnClickListener {
            tag=13
            showPictureDialog()

        } // end addProductSubImageBtn.setOnClickListener



    } // end onCreate

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    } // end onSupportNavigateUp

    private fun fieldsValidation():Boolean{




        if(carBrandEt.text.toString()==""){
            return false
        }
        if(carModelEt.text.toString()==""){
            return false
        }

        if(yearOfMakeEt.text.toString()==""){
            return false
        }

        if(engineCylindersSpinner.selectedItem.toString() == "" || engineCylindersSpinner.selectedItem.toString().equals("Type of Engine")){
            return false
        }
        if(transmissionSpinner.selectedItem.toString() == "" || transmissionSpinner.selectedItem.toString().equals("Type of Transmission")){
            return false
        }

        if(carPrice.text.toString()==""){
            return false
        }
        if(carMileage.text.toString()=="" ){
            return false
        }
        if(extColor.text.toString()=="" ){
            return false
        }
        if(intColor.text.toString()=="" ){
            return false
        }
        if(productDescriptionEt.text.toString() ==""){
            return false
        }
        if( tag==0){
            return false
        }
        return true
    } // end checkNull

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

        // main Image
        if (tag==12) {
            if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM &&
                resultCode == Activity.RESULT_OK &&
                data != null && data.data != null
            ) {
                filePath = data.data

//            val auxFile = File(filePath.toString())

                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                    mainProductPhotoIMG!!.setImageBitmap(bitmap)
                } // end try
                catch (e: IOException) {
                    e.printStackTrace()
                } // end catch

            } // end if

            else if (requestCode == REQUEST_TAKE_PHOTO &&
                resultCode == Activity.RESULT_OK
            ) {

//            val auxFile = File(mCurrentPhotoPath)

                val bitmap: Bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath)
                mainProductPhotoIMG.setImageBitmap(bitmap)

            } // end else if

        } // end Larger if

        // sub images
        if (tag==13) {
            if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM &&
                resultCode == Activity.RESULT_OK &&
                data != null && data.data != null
            ) {
                filePath = data.data

//            val auxFile = File(filePath.toString())

                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                    when {
                        subImagesArrayList.size == 0 -> subImage1.setImageBitmap(bitmap)
                        subImagesArrayList.size == 1 -> subImage2.setImageBitmap(bitmap)
                        subImagesArrayList.size == 2 -> subImage3.setImageBitmap(bitmap)
                        subImagesArrayList.size == 3 -> subImage4.setImageBitmap(bitmap)
                        subImagesArrayList.size == 4 -> {subImage5.setImageBitmap(bitmap)
                            addProductSubImageBtn.visibility= View.INVISIBLE}
                    } // end when

                    val imageRef = storageReference!!.child("images/" + UUID.randomUUID().toString())
                    imageRef.putFile(filePath!!)
                        .addOnSuccessListener {
                            val result = it.metadata!!.reference!!.downloadUrl
                            result.addOnSuccessListener {

                                subImagesArrayList.add(it.toString())

                            } // end result.addOnSuccessListener
                        } // end imageRef.putFile(filePath!!).addOnSuccessListener
                        .addOnFailureListener {  }
                } // end try
                catch (e: IOException) {
                    e.printStackTrace()
                } // end catch

            } // end if

            else if (requestCode == REQUEST_TAKE_PHOTO &&
                resultCode == Activity.RESULT_OK
            ) {

//            val auxFile = File(mCurrentPhotoPath)

                val bitmap: Bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath)
                when {
                    subImagesArrayList.size == 0 -> subImage1.setImageBitmap(bitmap)
                    subImagesArrayList.size == 1 -> subImage2.setImageBitmap(bitmap)
                    subImagesArrayList.size == 2 -> subImage3.setImageBitmap(bitmap)
                    subImagesArrayList.size == 3 -> subImage4.setImageBitmap(bitmap)
                    subImagesArrayList.size == 4 -> {subImage5.setImageBitmap(bitmap)
                        addProductSubImageBtn.visibility= View.INVISIBLE}

                } // end when

                val imageRef = storageReference!!.child("images/" + UUID.randomUUID().toString())
                imageRef.putFile(filePath!!)
                    .addOnSuccessListener {
                        val result = it.metadata!!.reference!!.downloadUrl
                        result.addOnSuccessListener {

                            subImagesArrayList.add(it.toString())


                        } // end result.addOnSuccessListener
                    } // end imageRef.putFile(filePath!!).addOnSuccessListener
                    .addOnFailureListener {  }


            } // end else if

        } // end Larger if

    } // end onActivityResult

    companion object {
        private const val REQUEST_TAKE_PHOTO = 1
        private const val REQUEST_SELECT_IMAGE_IN_ALBUM = 0
    }



} // end AddProductActivity

