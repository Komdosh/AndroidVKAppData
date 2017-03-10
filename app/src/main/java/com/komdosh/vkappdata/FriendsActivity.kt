package com.komdosh.vkappdata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.komdosh.vkappdata.adapter.FriendsAdapter
import com.komdosh.vkappdata.model.Response
import org.jetbrains.anko.async
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.uiThread

class FriendsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.friends_activity)

        startActivityForResult<AuthActivity>(1)
    }


    fun makeRequest(token: String, uid: String){
        val req = Request(uid, token)

        async {
            val result = req.execute()
            uiThread { showFriendsList(result) }
        }
    }

    fun showFriendsList(res: Response){
        val friendsList : RecyclerView = find(R.id.friendsList)
        friendsList.layoutManager = LinearLayoutManager(this)
        friendsList.adapter = FriendsAdapter(res.response)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> if (resultCode == Activity.RESULT_OK) {
                makeRequest(data.getStringExtra("token"), data.getStringExtra("uid"))
            }
        }
    }
}
