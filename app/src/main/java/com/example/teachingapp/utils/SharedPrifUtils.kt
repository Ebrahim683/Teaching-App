package com.example.teachingapp.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPrifUtils(val context:Context) {

    private val SHAREDPREF = "com.example.teachingapp"

    fun saveUser(email:String){
        val sharedPreferences = context.getSharedPreferences(SHAREDPREF,MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userEmail",email)
        editor.putBoolean("logged",true)
        editor.apply()
    }

    fun getUserEmail():String{
        val sharedPreferences = context.getSharedPreferences(SHAREDPREF,MODE_PRIVATE)
        return sharedPreferences.getString("userEmail","N/A").toString()
    }

    fun isLoggedIn():Boolean{
        val sharedPreferences = context.getSharedPreferences(SHAREDPREF,MODE_PRIVATE)
        return sharedPreferences.getBoolean("logged",false)
    }

    fun logOut(){
        val sharedPreferences = context.getSharedPreferences(SHAREDPREF,MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }


}