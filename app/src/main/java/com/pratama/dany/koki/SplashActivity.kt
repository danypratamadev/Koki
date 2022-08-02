package com.pratama.dany.koki

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    private var idCabang = 0
    private var namaCabang = ""
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPreferences = getSharedPreferences("SIGN_IN", Context.MODE_PRIVATE)
        val LOG = sharedPreferences.getBoolean("LOG", false)
        idCabang = sharedPreferences.getInt("cabang", 0)
        namaCabang = sharedPreferences.getString("namacabang", "").toString()

        if(LOG){

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()

        } else {

            Handler().postDelayed({

                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()

            }, 1500)

        }

    }

}
