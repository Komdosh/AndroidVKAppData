package com.komdosh.vkappdata

import com.google.gson.Gson
import com.komdosh.vkappdata.model.Response

/**
 * Created by komdosh on 10.03.17.
 */

class Request(val uid: String, val token: String) {
    fun execute(): Response {
        val usersJsonStr = java.net.URL("https://api.vk.com/method/friends.get?user_id=$uid&fields=first_name,last_name&access_token=$token").readText()
        return Gson().fromJson(usersJsonStr, Response::class.java)
    }
}