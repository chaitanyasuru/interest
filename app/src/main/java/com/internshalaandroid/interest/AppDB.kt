package com.internshalaandroid.interest

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities =[(App_Entity1::class)],version=1)
abstract class AppDB: RoomDatabase()
{
    abstract fun clientDao(): App_DAO
}