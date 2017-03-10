package com.komdosh.vkappdata.model

/**
 * Created by komdosh on 09.03.17.
 */
data class Response(val response : List<User>)
data class User(val first_name: String, val last_name: String, val online : Int, val uid : Int)