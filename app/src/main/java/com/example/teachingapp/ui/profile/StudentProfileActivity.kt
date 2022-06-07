package com.example.teachingapp.ui.profile

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.example.teachingapp.Application
import com.example.teachingapp.R
import com.example.teachingapp.data.model.viewmodel.MainViewModel
import com.example.teachingapp.data.model.viewmodel.ViewModelFactory
import com.example.teachingapp.utils.Status
import kotlinx.coroutines.runBlocking

private const val TAG = "studentProfileActivity"

class StudentProfileActivity : AppCompatActivity() {

	private val studentProfileViewModel by viewModels<MainViewModel> {
		ViewModelFactory((application as Application).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_student_profile)

		runBlocking {
			getUserProfile()
			getSingleStudentResult()
		}

	}

	private suspend fun getUserProfile() {
		studentProfileViewModel.getSingleUser("sabbir1054@gmail.com").asLiveData().observe(this) {
			Log.d(TAG, "getUserProfile: get user profile response: ${it.status}")
			when (it.status) {
				Status.LOADING -> {
					Log.d(TAG, "getUserProfile: Loading...")
					Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
				}
				Status.SUCCESS -> {
					Log.d(TAG, "getUserProfile: success: ${it.data}")
					Toast.makeText(this, "${it.data}", Toast.LENGTH_SHORT).show()
				}
				Status.ERROR -> {
					Log.d(TAG, "getUserProfile: error: ${it.message}")
				}
				null -> {
					Log.d(TAG, "getUserProfile: null!!!")
				}
			}
		}
	}

	private suspend fun getSingleStudentResult() {
		studentProfileViewModel.getSingleStudentResult("5785756").asLiveData().observe(this) {
			Log.d(TAG, "getSingleStudentResult: single student result response ${it.status}")

			when (it.status) {
				Status.LOADING -> {
					Log.d(TAG, "getSingleStudentResult: Loading...")
					Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
				}
				Status.SUCCESS -> {
					Log.d(TAG, "getSingleStudentResult: success: ${it.data}")
					Toast.makeText(this, "${it.data}", Toast.LENGTH_SHORT).show()
				}
				Status.ERROR -> {
					Log.d(TAG, "getSingleStudentResult: error: ${it.message}")
					Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
				}
				null -> {
					Log.d(TAG, "getSingleStudentResult: null!!!")
				}
			}
		}
	}
}