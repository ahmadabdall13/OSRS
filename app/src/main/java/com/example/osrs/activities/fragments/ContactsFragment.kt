package com.example.osrs.activities.fragments
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.osrs.R
import kotlinx.android.synthetic.main.fragment_contact.*


/**
 * A simple [Fragment] subclass.
 */
class ContactsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contact, container, false)

       // contacting_info.text="If you have any inquiries or questions please send them to the following E-mail :\n OSRS@gmail.com"
        var x = 3
        x+=4

    return view} // end onCreateView

} // end ContactsFragment