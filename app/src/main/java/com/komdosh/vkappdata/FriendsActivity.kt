package com.komdosh.vkappdata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.komdosh.vkappdata.adapter.FriendsAdapter
import com.komdosh.vkappdata.model.User
import org.jetbrains.anko.startActivityForResult
import org.json.JSONObject


class FriendsActivity : AppCompatActivity() {

    val listOfUsers = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.friends_activity)

        startActivityForResult<AuthActivity>(1)
    }


    fun makeRequest(token: String, uid: String){
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.vk.com/method/friends.get?user_id=$uid&fields=first_name,last_name&access_token=$token"

        val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response -> showFriendsList(response) },
                Response.ErrorListener { })

        queue.add(stringRequest)
    }

    fun showFriendsList(string: String){
        val jsonArray = JSONObject(string).getJSONArray("response")

        (0..jsonArray.length()-1)
                .map { JSONObject(jsonArray[it].toString()) }
                .mapTo(listOfUsers) { User(it.getString("first_name"), it.getString("last_name"), it.getInt("online"), it.getInt("uid")) }

        val friendsList = findViewById(R.id.friendsList) as RecyclerView
        friendsList.layoutManager = LinearLayoutManager(this)
        friendsList.adapter = FriendsAdapter(listOfUsers)
        friendsList.adapter.notifyDataSetChanged()
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
