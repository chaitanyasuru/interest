package com.internshalaandroid.interest.fragments

import android.content.Context
//import android.graphics.Insets.add
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.internshalaandroid.interest.R
import com.internshalaandroid.interest.activity.MyDbHelper
import com.internshalaandroid.interest.adapters.client_adapter
import com.internshalaandroid.interest.model.Cdata


class clients : Fragment() {
    lateinit var recyclerclient: RecyclerView
    lateinit var layoutmanager: RecyclerView.LayoutManager
    lateinit var recycleadapter: client_adapter
    lateinit var cname: String
    lateinit var cph: String
    lateinit var caadhar: String
    lateinit var caddress: String
    lateinit var camount: String
    lateinit var cinterest: String
    lateinit var cdataTime:String
    lateinit var cgap:String
    lateinit var cintial:String
    val bookinfolist: ArrayList<Cdata> = arrayListOf<Cdata>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_clients, container, false)

        // Inflate the layout for this fragment

        var helper = MyDbHelper(activity!!.applicationContext)
        var db = helper.readableDatabase

        var rs = db.rawQuery("select * from users", null)

        if(rs!=null && rs.count>0)
        {
    while(rs.moveToNext()) {
        //Toast.makeText(activity,rs.getString(1), Toast.LENGTH_SHORT).show()
        cname = rs.getString(0)
        cph = rs.getString(1)
        caadhar = rs.getString(2)
        caddress = rs.getString(3)
        camount = rs.getString(4)
        cinterest = rs.getString(5)
        cdataTime=rs.getString(6)
        cgap=rs.getString(7)
        cintial=rs.getString(8)
       val e1=Cdata(cname.toString(), cph.toString(), caadhar.toString(), caddress.toString(), camount.toString(), cinterest.toString(),cdataTime,cgap.toString(),cintial.toString())
      bookinfolist.add(e1)
    }
    }
        else
        {
            Toast.makeText(context,"No clients",Toast.LENGTH_SHORT).show()
        }

        recyclerclient               = view.findViewById(R.id.recyclerclient)
        layoutmanager                = LinearLayoutManager(activity)
        recycleadapter               = client_adapter(activity as Context, bookinfolist)
        recyclerclient.adapter       = recycleadapter
        recyclerclient.layoutManager = layoutmanager
        recyclerclient.addItemDecoration(
            DividerItemDecoration(
                recyclerclient.context, (layoutmanager as LinearLayoutManager).orientation
            )
        )
        return view
    }



}