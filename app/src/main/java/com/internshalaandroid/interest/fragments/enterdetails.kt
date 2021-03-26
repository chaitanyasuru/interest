package com.internshalaandroid.interest.fragments

//import com.internshalaandroid.interest.App_Entity
import android.content.ContentValues
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.text.TextUtils.isDigitsOnly
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import com.internshalaandroid.interest.R
import com.internshalaandroid.interest.activity.MyDbHelper
import kotlinx.android.synthetic.main.fragment_enterdetails.*


class enterdetails : Fragment() {

    lateinit var button:Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_enterdetails, container, false)
        button= view.findViewById(R.id.BtnSave)

        var helper=MyDbHelper(activity!!.applicationContext)

        var db=helper.readableDatabase

        button.setOnClickListener(object : View.OnClickListener {




            @RequiresApi(Build.VERSION_CODES.N)
            override fun onClick(v: View?) {

                var  n1=edtName.text.toString()
                var  n2=edtPhone.text.toString()
                var n3=edtaadhar.text.toString()
                var  n4=edtAddress.text.toString()
                var n5= edtamount.text.toString()
                var n6=edtinterest.text.toString()
                var n7:String=gap.text.toString()
                val calendar: Calendar = Calendar.getInstance()
                val simpleDateFormat = SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a")
                var dateTime = simpleDateFormat.format(calendar.getTime())
                var cv = ContentValues()
                n1.toString()

                if(n1.isNotEmpty() && n2.isNotEmpty() && n3.isNotEmpty() && n4.isNotEmpty() && n5.isNotEmpty() && n6.isNotEmpty() && n7.isNotEmpty()) {
                    if(!isDigitsOnly(n1)) {
                        if(isDigitsOnly(n2)&&n2.length==10) {
                            if(n3.length==12 && isDigitsOnly(n3))
                            {
                                if(isDigitsOnly(n5)) {
                                    if(isDigitsOnly(n6)) {
                                        cv.put("UNAME", edtName.text.toString())
                                        cv.put("PHONE", edtPhone.text.toString())
                                        cv.put("AADHAR", edtaadhar.text.toString())
                                        cv.put("ADDRESS", edtAddress.text.toString())
                                        cv.put("AMOUNT", edtamount.text.toString())
                                        cv.put("INTEREST", edtinterest.text.toString())
                                        cv.put("DATETIME", dateTime.toString())
                                        cv.put("TIME", gap.text.toString())
                                        cv.put("IntialAmount", edtamount.text.toString())
                                        db.insert("USERS", null, cv)
                                        //Toast.makeText(context,dateTime,Toast.LENGTH_SHORT).show()
                                        edtName.setText("")
                                        edtPhone.setText("")
                                        edtaadhar.setText("")
                                        edtAddress.setText("")
                                        edtamount.setText("")
                                        edtinterest.setText("")
                                        gap.setText("")
                                        edtName.requestFocus()
                                        Toast.makeText(activity, "Details saved", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                    else
                                    {
                                        Toast.makeText(activity, "please enter only numbers in interest like 3 or 4", Toast.LENGTH_SHORT).show()

                                    }
                                }
                                else
                                {
                                    Toast.makeText(activity, "enter the amount correctly", Toast.LENGTH_SHORT).show()

                                }
                            }
                            else
                            {
                                Toast.makeText(activity, "please enter 12 digit aadhar number", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else
                        {
                            Toast.makeText(activity,"Please enter phone number correctly",Toast.LENGTH_SHORT).show()

                        }
                    }
                }
                else
                {
                    Toast.makeText(activity,"Please fill all the fields",Toast.LENGTH_SHORT).show()
                }
            }
        })
        return view

    }
}


