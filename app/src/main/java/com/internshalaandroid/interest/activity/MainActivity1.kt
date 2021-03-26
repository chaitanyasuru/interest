package com.internshalaandroid.interest.activity

import android.Manifest.permission.CALL_PHONE
import android.Manifest.permission.SEND_SMS
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.internshalaandroid.interest.R
import com.internshalaandroid.interest.about
import com.internshalaandroid.interest.fragments.clients
import com.internshalaandroid.interest.fragments.enterdetails
import com.internshalaandroid.interest.fragments.home
import java.lang.Exception

class MainActivity1 : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var navigationView: NavigationView
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var frameLayout: FrameLayout
    var previousmenuitem:MenuItem?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawerlayout)
        navigationView = findViewById(R.id.navigationView)
        coordinatorLayout = findViewById(R.id.coordinatelayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.framelayout)
        setUpToolbar()
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity1,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        openHome()
        if (ContextCompat.checkSelfPermission(
                this@MainActivity1,
                CALL_PHONE
            )
            == PackageManager.PERMISSION_DENIED)
        {
        ch()}
        if (ContextCompat.checkSelfPermission(
                this@MainActivity1,
                SEND_SMS
            )
            == PackageManager.PERMISSION_DENIED
           )
        { c()
            }
        navigationView.setNavigationItemSelectedListener {

            if (previousmenuitem != null) {
                previousmenuitem?.isChecked = false
            }
            it.isCheckable = true
            it.isCheckable = true
            previousmenuitem = it
            when (it.itemId) {
                R.id.home -> {
                    openHome()
                    drawerLayout.closeDrawers()
                }
                R.id.enterdetails -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.framelayout,
                            enterdetails()
                        )
                        .commit()
                    supportActionBar?.title = "Enter details"
                    drawerLayout.closeDrawers()
                }
                R.id.clients -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framelayout,
                        clients()
                    )
                        .commit()
                    supportActionBar?.title = "Clients"
                    drawerLayout.closeDrawers()
                }
                R.id.about -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framelayout,
                        about()
                    )
                        .commit()
                    supportActionBar?.title = "About"
                    drawerLayout.closeDrawers()
                }

            }

            return@setNavigationItemSelectedListener true
        }

    }

    fun setUpToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title="Developing"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id== android.R.id.home)
        {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun openHome()
    {
        val fragment= home()
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.framelayout,fragment)
        transaction.commit()
        supportActionBar?.title="Home"
        navigationView.setCheckedItem(R.id.home)
     }

    override fun onBackPressed() {
        val f=supportFragmentManager.findFragmentById(R.id.framelayout)
        when(f) {
            !is home -> openHome()
        else -> super.onBackPressed()
        }

    }


    fun checkPermission(permission: String, requestCode: Int) {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                this@MainActivity1,
                permission
            )
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat
                .requestPermissions(
                    this@MainActivity1, arrayOf(permission),
                    requestCode
                )

        }
    }
   fun checkPermission1(permission:String, requestCode: Int) {
           // Checking if permission is not granted
           if (ContextCompat.checkSelfPermission(
                   this@MainActivity1,
                   permission
               )
               == PackageManager.PERMISSION_DENIED
           ) {
               ActivityCompat
                   .requestPermissions(
                       this@MainActivity1, arrayOf(permission),
                       requestCode
                   )
           } else {
               Toast
                   .makeText(
                       this@MainActivity1,
                       "Permission for calls granted already",
                       Toast.LENGTH_SHORT
                   )
                   .show()
           }
       }
fun ch()
{
    val builder= AlertDialog.Builder(this)
    builder.setTitle("ALERT!!")
    //set message for alert dialog
    builder.setMessage("For calls we need permission please click ok")
    builder.setIcon(R.drawable.ic_dialag_alert1)
    builder.setPositiveButton("ok")
    {
            dialog, which ->

        try {
           checkPermission1(CALL_PHONE,101)
        }
        catch (e:Exception)
        {
        }

    }
    builder.setNegativeButton("no")
    {
            dialog, which ->
        dialog.dismiss()
    }
    builder.show()
}
    fun c()
    {
        val builder= AlertDialog.Builder(this)
        builder.setTitle("ALERT!!")
        //set message for alert dialog
        builder.setMessage("For sending messages we need permission please click ok")
        builder.setIcon(R.drawable.ic_dialag_alert1)
        builder.setPositiveButton("ok")
        {
                dialog, which ->

            try {
                checkPermission(SEND_SMS,101)
            }
            catch (e:Exception)
            {
            }

        }
        builder.setNegativeButton("no")
        {
                dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

}