package com.example.teachingapp.data.local.remote

import com.example.teachingapp.data.model.datamodel.attendance.AttendanceByDateModel
import com.example.teachingapp.data.model.datamodel.attendance.AttendanceCourseModel
import com.example.teachingapp.data.model.datamodel.coursemodel.CoursesModel
import com.example.teachingapp.data.model.datamodel.response.RegistrationResponse
import com.example.teachingapp.data.model.datamodel.response.UpdateProfileResponse
import com.example.teachingapp.data.model.datamodel.resultmodel.AllStudentsResultModel
import com.example.teachingapp.data.model.datamodel.resultmodel.SingleStudentResultModel
import com.example.teachingapp.data.model.datamodel.studentmodel.StudentRegistrationModel
import com.example.teachingapp.data.model.datamodel.teachermodel.TeacherCourseModel
import com.example.teachingapp.data.model.datamodel.teachermodel.TeacherRegisterModel
import com.example.teachingapp.data.model.datamodel.updateprofile.UpdateProfileModel
import com.example.teachingapp.data.model.datamodel.usermodel.AllUsersModelItem
import com.example.teachingapp.data.model.datamodel.usermodel.SingleUserModel
import retrofit2.Response
import retrofit2.http.*

interface ApiResponse {

	@POST("/users")
	suspend fun studentRegister(@Body studentRegistrationModel: StudentRegistrationModel): Response<RegistrationResponse>

	@POST("/users")
	suspend fun teacherRegister(@Body teacherRegisterModel: TeacherRegisterModel): Response<RegistrationResponse>

	@GET("/users/{email}")
	@Streaming
	suspend fun getSingleUser(@Path("email") email: String): Response<SingleUserModel>

	@PUT("/users/{email}")
	@Streaming
	suspend fun updateSingleUser(
		@Path("email") email: String,
		@Body updateProfileModel: UpdateProfileModel
	): Response<UpdateProfileResponse>

	@GET("/users")
	suspend fun getAllUsers(): Response<List<AllUsersModelItem>>

	@GET("/courses/{courseCode}")
	suspend fun showEnrollCourses(@Path("courseCode") courseCode: String): Response<CoursesModel>

	@GET("/courses/{courseId}")
	suspend fun showTeacherCourses(@Path("courseId") courseId: String): Response<TeacherCourseModel>

	@GET("/courses")
	suspend fun showCourses(): Response<List<CoursesModel>>

	@GET("/results")
	suspend fun getAllStudentsResult(): Response<List<AllStudentsResultModel>>

	@GET("/results/{id}")
	suspend fun getSingleStudentResult(@Path("id") id: String): Response<SingleStudentResultModel>

	@GET("/attendance/{courseId}")
	suspend fun getStudentAttendance(@Path("courseId") courseId: String): Response<List<AttendanceCourseModel>>

	@GET("/attendance/{courseId}/{date}")
	suspend fun getStudentAttendanceByDate(
		@Path("courseId") courseId: String,
		@Path("date") date: String
	): Response<List<AttendanceByDateModel>>
}