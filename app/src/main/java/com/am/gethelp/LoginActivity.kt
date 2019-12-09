package com.am.gethelp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.loginBtn

class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        loginBtn.setOnClickListener {
            startActivity(Intent(this@LoginActivity, DrawerActivity::class.java))
        }

    }
}
