package com.example.teachingapp.data.model.viewmodel

import androidx.lifecycle.ViewModel
import com.example.teachingapp.utils.Repository
import com.example.teachingapp.utils.Resource
import kotlinx.coroutines.flow.flow

class MainViewModel(private val repository: Repository) :
	ViewModel() {

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

	//for get courses
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

}