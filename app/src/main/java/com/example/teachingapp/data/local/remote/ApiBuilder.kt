package com.example.teachingapp.data.local.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiBuilder {

	private val BASE_URL = "https://stormy-forest-12943.herokuapp.com"

	private fun buildApi(): Retrofit {
		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}

		companion object{
			fun getApiService(): ApiResponse = ApiBuilder().buildApi().create(ApiResponse::class.java)
		}

}