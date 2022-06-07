package com.example.teachingapp.utils

import com.example.teachingapp.data.local.remote.ApiBuilder

class Repository {

	//for get single user
	suspend fun getSingleUser(email: String) = ApiBuilder.getApiService().getSingleUser(email)

	//for get all users
	suspend fun getAllUsers() = ApiBuilder.getApiService().getAllUsers()

	//for get courses
	suspend fun showCourses() = ApiBuilder.getApiService().showCourses()

	//for get all students result
	suspend fun getAllStudentsResult() = ApiBuilder.getApiService().getAllStudentsResult()

	//for get single student result
	suspend fun getSingleStudentResult(id:String) = ApiBuilder.getApiService().getSingleStudentResult(id)

}