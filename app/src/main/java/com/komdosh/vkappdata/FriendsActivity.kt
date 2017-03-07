package com.komdosh.vkappdata

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.longToast

class FriendsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.friends_activity)
        longToast(intent.getCharSequenceExtra("email"))
        longToast(intent.getStringExtra("password"))
    }
}
