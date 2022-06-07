package com.example.teachingapp.utils

class Resource<out T>(val data: T?, val message: String?, val status: Status?) {

	companion object {

		fun <T> loading(data: T?): Resource<T> =
			Resource(data = data, message = "Loading...", status = Status.LOADING)

		fun <T> success(data: T?): Resource<T> =
			Resource(data = data, message = null, status = Status.SUCCESS)

		fun <T> error(data: T?, message: String?): Resource<T> =
			Resource(data = data, message = message, status = Status.ERROR)

	}

}