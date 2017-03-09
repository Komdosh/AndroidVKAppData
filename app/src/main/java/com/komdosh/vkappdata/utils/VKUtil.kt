package com.komdosh.vkappdata.utils

import java.util.regex.Pattern

/**
 * Created by komdosh on 09.03.17.
 */
class VKUtil {
    fun parseRedirectUrl(url: String): Array<String>  {
        val accessToken = extractPattern(url, "access_token=(.*?)&");
        val userId = extractPattern(url, "user_id=(\\d*)");
        if( userId == null || userId.isEmpty() || accessToken == null || accessToken.isEmpty() ) {
            throw Exception("Failed to parse redirect url $url")
        }
        return arrayOf(accessToken, userId)
    }

    fun extractPattern(string: String, pattern: String): String?{
        val p = Pattern.compile(pattern)
        val m = p.matcher(string)
        if (!m.find())
            return null
        return m.toMatchResult().group(1)
    }
}