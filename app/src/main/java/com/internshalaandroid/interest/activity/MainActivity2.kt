
package com.internshalaandroid.interest.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.os.HandlerCompat.postDelayed
import com.internshalaandroid.interest.R

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_main)
        supportActionBar?.hide()
        Handler().postDelayed({
            val intent= Intent(this@MainActivity2,MainActivity1::class.java)
            startActivity(intent)
            finish()
        }, 4000)

    }
}