package com.giyadabi.appat_admin

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context){
    private var prefs: SharedPreferences = context.getSharedPreferences("KEY", Context.MODE_PRIVATE)


    companion object{
        const val USER_TOKEN = "user_token"
        const val VERIFICATION_USER = "VERIFICATION_USER"
    }

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
        editor.commit()
    }

    fun fetchVerification(): String {
        return prefs.getString(VERIFICATION_USER,"tes").toString()
    }
}
