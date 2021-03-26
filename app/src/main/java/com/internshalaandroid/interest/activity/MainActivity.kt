package com.internshalaandroid.interest.activity
import android.Manifest
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.internshala.interest.FingerPrintHelper
import com.internshalaandroid.interest.R
import java.lang.Exception
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class MainActivity : AppCompatActivity() {
    lateinit var fm:FingerprintManager
    lateinit var km:KeyguardManager
    lateinit var keyStore: KeyStore
    lateinit var keyGenerator: KeyGenerator
    var KEY_NAME="my_key"
    lateinit var cipher:Cipher
    lateinit var cryptoObject:FingerprintManager.CryptoObject
       @RequiresApi(Build.VERSION_CODES.M)
       override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_main)
           km=getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
           fm=getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
           if(!km.isKeyguardSecure){
               Toast.makeText(this,"Lock screen security not enabled in settings",Toast.LENGTH_LONG).show()
               return
           }
         if(!fm.hasEnrolledFingerprints()){
             Toast.makeText(this,"Register atleast one fingerprint in settings",Toast.LENGTH_LONG).show()
             return
         }
           if(ActivityCompat.checkSelfPermission(this,Manifest.permission.USE_FINGERPRINT)!=PackageManager.PERMISSION_GRANTED)
           {
               ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.USE_FINGERPRINT),111)
           }
           else
               validateFingerPrint()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==111 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            validateFingerPrint()
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun validateFingerPrint() {//generate the key
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            keyStore.load(null)
            keyGenerator.run {
                init(
                        KeyGenParameterSpec.Builder(
                            KEY_NAME,
                            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                        )
                            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                            .setUserAuthenticationRequired(true)
                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                            .build())
                    keyGenerator.generateKey()
            }
        } catch (e: Exception) {
        }
//Initialisation of cryptography
        if(initCipher()) {
            cipher.let {
                cryptoObject=FingerprintManager.CryptoObject(it)
            }
        }


        var helper= FingerPrintHelper(this)
        if(fm!=null && cryptoObject!=null)
        {
            helper.startAuth(fm,cryptoObject)
        }
    }

    private fun initCipher(): Boolean {
      try{
          cipher= Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES+"/"
                  +KeyProperties.BLOCK_MODE_CBC+"/"+KeyProperties.ENCRYPTION_PADDING_PKCS7)
      }
      catch (e:Exception){
      }
        try {
           keyStore.load(null)
            val key=keyStore.getKey(KEY_NAME,null) as SecretKey
            cipher.init(Cipher.ENCRYPT_MODE,key)
            return true
        }
        catch(e:Exception){
            return false
        }
    }
}
