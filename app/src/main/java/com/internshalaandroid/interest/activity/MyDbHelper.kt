package com.internshalaandroid.interest.activity

import android.Manifest.permission_group.PHONE
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.internshalaandroid.interest.fragments.clients
import java.lang.Exception
import java.nio.file.attribute.UserPrincipalLookupService

class MyDbHelper(context: Context): SQLiteOpenHelper(context,"USERDB",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("Create table USERS(UNAME TEXT,PHONE TEXT, AADHAR TEXT,ADDRESS TEXT,AMOUNT TEXT,INTEREST TEXT,DATETIME TEXT,TIME TEXT,IntialAmount Text)")
       //db?.execSQL("INSERT INTO USERS values('chay','9866337744','273828292929','RAJAM','7373','2%')")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
    fun deleteData(ph:String):Int{
        val db=this.writableDatabase
        return db.delete("USERS","PHONE=?", arrayOf(ph))

    }
    fun update(client_ph:String,client_amount:String): Boolean {
        val db=this.writableDatabase
        val cv=ContentValues()
        var result:Boolean=false
        cv.put("AMOUNT",client_amount)
        cv.put("PHONE",client_ph)
        try {
            db.update("USERS", cv, "PHONE=?", arrayOf(client_ph))
            result=true
        }
        catch (e:Exception)
        {
            result=false
        }
        return result
    }
    companion object
    {
        val DATABASE_NAME="USERDB.db"
        val TABLE_NAME="USERS"
        val col1="UNAME"
        val col2="PHONE"
        val col3="AADHAR"
        val col4="ADDRESS"
        val col5="AMOUNT"
        val col6="INTEREST"
    }
}