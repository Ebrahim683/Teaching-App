package com.example.teachingapp.data.local.remote

import com.example.teachingapp.data.model.datamodel.coursemodel.CoursesModelItem
import com.example.teachingapp.data.model.datamodel.resultmodel.AllStudentsResultModel
import com.example.teachingapp.data.model.datamodel.resultmodel.SingleStudentResultModel
import com.example.teachingapp.data.model.datamodel.usermodel.AllUsersModelItem
import com.example.teachingapp.data.model.datamodel.usermodel.SingleUserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface ApiResponse {

	@GET("/users/{email}")
	@Streaming
	suspend fun getSingleUser(@Path("email") email: String): Response<SingleUserModel>

	@GET("/users")
	suspend fun getAllUsers(): Response<List<AllUsersModelItem>>

	@GET("/courses")
	suspend fun showCourses(): Response<List<CoursesModelItem>>

	@GET("/results")
	suspend fun getAllStudentsResult(): Response<List<AllStudentsResultModel>>

	@GET("/results/{id}")
	suspend fun getSingleStudentResult(@Path("id") id: String): Response<SingleStudentResultModel>
}