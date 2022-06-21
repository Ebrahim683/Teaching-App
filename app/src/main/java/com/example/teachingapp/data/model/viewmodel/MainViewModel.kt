package com.example.teachingapp.data.model.viewmodel

import androidx.lifecycle.ViewModel
import com.example.teachingapp.data.model.datamodel.studentmodel.StudentRegistrationModel
import com.example.teachingapp.data.model.datamodel.teachermodel.TeacherRegisterModel
import com.example.teachingapp.data.model.datamodel.updateprofile.UpdateProfileModel
import com.example.teachingapp.data.local.repository.Repository
import com.example.teachingapp.utils.Resource
import kotlinx.coroutines.flow.flow

class MainViewModel(private val repository: Repository) :
	ViewModel() {

	//student registration
	fun studentRegister(studentRegistrationModel: StudentRegistrationModel) = flow {
		emit(Resource.loading(null))

		try {
			val response = repository.studentRegister(studentRegistrationModel)

			if (response.isSuccessful) {
				when (response.code()) {
					200 -> {
						emit(Resource.success(response.body()))
					}
					else -> {
						emit(Resource.error(null, response.errorBody().toString()))
					}
				}
			} else {
				emit(Resource.error(null, response.errorBody().toString()))
			}

		} catch (e: Exception) {
			emit(Resource.error(null, e.message))
		}

	}

	//teacher register
	fun teacherRegister(teacherRegisterModel: TeacherRegisterModel) = flow {
		emit(Resource.loading(null))

		try {
			val response = repository.teacherRegister(teacherRegisterModel)

			if (response.isSuccessful) {
				when (response.code()) {
					200 -> {
						emit(Resource.success(response.body()))
					}
					else -> {
						emit(Resource.error(null, response.errorBody().toString()))
					}
				}
			} else {
				emit(Resource.error(null, response.errorBody().toString()))
			}

		} catch (e: Exception) {
			emit(Resource.error(null, e.message))
		}

	}

	//for get single user
	fun getSingleUser(email: String) = flow {
		emit(Resource.loading(null))

		try {
			val response = repository.getSingleUser(email)

			if (response.isSuccessful) {
				when (response.code()) {
					200 -> {
						emit(Resource.success(response.body()))
					}
					else -> {
						emit(Resource.error(null, response.errorBody().toString()))
					}
				}
			} else {
				emit(Resource.error(null, response.errorBody().toString()))
			}

		} catch (e: Exception) {
			emit(Resource.error(null, e.message))
		}

	}

	//for get all users
	fun getAllUsers() = flow {
		emit(Resource.loading(null))

		try {
			val response = repository.getAllUsers()

			if (response.isSuccessful) {
				when (response.code()) {
					200 -> {
						emit(Resource.success(response.body()))
					}
					else -> {
						emit(Resource.error(null, response.errorBody().toString()))
					}
				}
			} else {
				emit(Resource.error(null, response.errorBody().toString()))
			}

		} catch (e: Exception) {
			emit(Resource.error(null, e.message))
		}

	}

	//for get all courses
	fun showCourses() = flow {
		emit(Resource.loading(null))

		try {
			val response = repository.showCourses()

			if (response.isSuccessful) {
				when (response.code()) {
					200 -> {
						emit(Resource.success(response.body()))
					}
					else -> {
						emit(Resource.error(null, response.errorBody().toString()))
					}
				}
			} else {
				emit(Resource.error(null, response.errorBody().toString()))
			}

		} catch (e: Exception) {
			emit(Resource.error(null, e.message))
		}

	}

	//for get enrolled courses
	fun showEnrolledCourses(email: String) = flow {
		emit(Resource.loading(null))

		try {
			val response = repository.showEnrolledCourses(email)

			if (response.isSuccessful) {
				when (response.code()) {
					200 -> {
						emit(Resource.success(response.body()))
					}
					else -> {
						emit(Resource.error(null, response.errorBody().toString()))
					}
				}
			} else {
				emit(Resource.error(null, response.errorBody().toString()))
			}

		} catch (e: Exception) {
			emit(Resource.error(null, e.message))
		}

	}

	//for get teacher courses
	fun showTeacherCourses(value: String) = flow {
		emit(Resource.loading(null))

		try {
			val response = repository.showTeacherCourses(value)

			if (response.isSuccessful) {
				when (response.code()) {
					200 -> {
						emit(Resource.success(response.body()))
					}
					else -> {
						emit(Resource.error(null, response.errorBody().toString()))
					}
				}
			} else {
				emit(Resource.error(null, response.errorBody().toString()))
			}

		} catch (e: Exception) {
			emit(Resource.error(null, e.message))
		}

	}

	//for get all students result
	fun getAllStudentsResult() = flow {
		emit(Resource.loading(null))

		try {
			val response = repository.getAllStudentsResult()

			if (response.isSuccessful) {
				when (response.code()) {
					200 -> {
						emit(Resource.success(response.body()))
					}
					else -> {
						emit(Resource.error(null, response.errorBody().toString()))
					}
				}
			} else {
				emit(Resource.error(null, response.errorBody().toString()))
			}


		} catch (e: Exception) {
			emit(Resource.error(null, e.message))
		}

	}

	//for get single student result
	fun getSingleStudentResult(id: String) = flow {
		emit(Resource.loading(null))

		try {
			val response = repository.getSingleStudentResult(id)

			if (response.isSuccessful) {
				when (response.code()) {
					200 -> {
						emit(Resource.success(response.body()))
					}
					else -> {
						emit(Resource.error(null, response.errorBody().toString()))
					}
				}
			} else {
				emit(Resource.error(null, response.errorBody().toString()))
			}

		} catch (e: Exception) {
			emit(Resource.error(null, e.message))
		}

	}


	//update profile
	fun updateProfile(email: String, updateProfileModel: UpdateProfileModel) = flow {
		emit(Resource.loading(null))

		try {
			val response = repository.updateProfile(email, updateProfileModel)
			if (response.isSuccessful) {
				when (response.code()) {
					200 -> {
						emit(Resource.success(response.body()))
					}
					else -> {
						emit(Resource.error(null, response.errorBody().toString()))
					}
				}
			} else {
				emit(Resource.error(null, response.errorBody().toString()))
			}

		} catch (e: Exception) {
			emit(Resource.error(null, e.message))
		}

	}

}