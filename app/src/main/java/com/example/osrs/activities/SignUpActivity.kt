package com.example.osrs.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.Toast
import com.example.osrs.R
import com.example.osrs.services.ServiceVolley
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        var Intent1: Intent
        Intent1= getIntent()
        val socialId = Intent1.getStringExtra("social_id")



        setSupportActionBar(sp_toolbar)
        val actionBar = supportActionBar
        actionBar!!.title= "Sign Up"


        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)


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

} // end SignUpActivity
