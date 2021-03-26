package com.internshalaandroid.interest

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface App_DAO
{
     @Insert
     fun saveclient1(client:App_Entity1)
     @Query("select * from App_Entity1")
     fun readclient():List<App_Entity1>
}