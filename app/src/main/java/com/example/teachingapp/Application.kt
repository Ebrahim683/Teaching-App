package com.example.teachingapp

import android.app.Application
import com.example.teachingapp.data.local.remote.ApiBuilder
import com.example.teachingapp.data.local.repository.Repository

class Application : Application() {

	private val apiBuilder by lazy {
		ApiBuilder()
	}

	val repository by lazy {
		Repository()
	}

}