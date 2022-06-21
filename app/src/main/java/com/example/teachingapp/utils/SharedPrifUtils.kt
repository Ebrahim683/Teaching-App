package com.example.teachingapp.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPrifUtils(val context: Context) {

	private val SHAREDPREF = "com.example.teachingapp"

	fun saveUser(role: String?=null) {
		val sharedPreferences = context.getSharedPreferences(SHAREDPREF, MODE_PRIVATE)
		val editor = sharedPreferences.edit()
		editor.putString("userRole", role)
		editor.putBoolean("logged", true)
		editor.apply()
	}

	fun getUserRole(): String {
		val sharedPreferences = context.getSharedPreferences(SHAREDPREF, MODE_PRIVATE)
		return sharedPreferences.getString("userRole", "N/A").toString()
	}

	fun isLoggedIn(): Boolean {
		val sharedPreferences = context.getSharedPreferences(SHAREDPREF, MODE_PRIVATE)
		return sharedPreferences.getBoolean("logged", false)
	}

	fun logOut() {
		val sharedPreferences = context.getSharedPreferences(SHAREDPREF, MODE_PRIVATE)
		val editor = sharedPreferences.edit()
		editor.clear()
		editor.apply()
	}


}