package com.example.teachingapp.data.local.repository

import com.example.teachingapp.data.local.remote.ApiBuilder
import com.example.teachingapp.data.model.datamodel.studentmodel.StudentRegistrationModel
import com.example.teachingapp.data.model.datamodel.teachermodel.TeacherRegisterModel
import com.example.teachingapp.data.model.datamodel.updateprofile.UpdateProfileModel

class Repository {

	//student registration
	suspend fun studentRegister(studentRegistrationModel: StudentRegistrationModel) =
		ApiBuilder.getApiService().studentRegister(studentRegistrationModel)

	//teacher registration
	suspend fun teacherRegister(teacherRegisterModel: TeacherRegisterModel) =
		ApiBuilder.getApiService().teacherRegister(teacherRegisterModel)

	//for get single user
	suspend fun getSingleUser(email: String) = ApiBuilder.getApiService().getSingleUser(email)

	//for get all users
	suspend fun getAllUsers() = ApiBuilder.getApiService().getAllUsers()

	//for get all courses
	suspend fun showCourses() = ApiBuilder.getApiService().showCourses()

	//for get enrolled courses
	suspend fun showEnrolledCourses(email:String) = ApiBuilder.getApiService().showEnrollCourses(email)

	//for get teachers courses
	suspend fun showTeacherCourses(value:String) = ApiBuilder.getApiService().showTeacherCourses(value)

	//for get all students result
	suspend fun getAllStudentsResult() = ApiBuilder.getApiService().getAllStudentsResult()

	//for get single student result
	suspend fun getSingleStudentResult(id: String) =
		ApiBuilder.getApiService().getSingleStudentResult(id)

	//update profile
	suspend fun updateProfile(email: String, updateProfileModel: UpdateProfileModel) =
		ApiBuilder.getApiService().updateSingleUser(email, updateProfileModel)

}