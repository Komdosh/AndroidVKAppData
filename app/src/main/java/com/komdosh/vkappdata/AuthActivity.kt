package com.komdosh.vkappdata

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.auth_activity.*
import org.jetbrains.anko.startActivity

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)

        button.setOnClickListener { startActivity<FriendsActivity>("email" to email.text.toString(), "password" to password.text.toString()) }
    }
}
