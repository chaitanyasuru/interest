package com.internshalaandroid.interest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class App_Entity1

{

    //@ColumnInfo(name="Client_name")
    var Client_name:String=" "
    @PrimaryKey
   // @ColumnInfo(name="Client_ph")
    var Client_ph:String=" "
  //  @ColumnInfo(name="Client_Aadhar")
    var Client_Aadhar:String=" "

  //  @ColumnInfo(name="Client_address")
    var Client_address:String=" "

   // @ColumnInfo(name="Client_amount")
    var Client_amount:String=" "

    //@ColumnInfo(name="Client_interest")
    var Client_interest:String=" "

}