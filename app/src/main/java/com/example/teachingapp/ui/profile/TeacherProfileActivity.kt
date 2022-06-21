package com.example.teachingapp.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import com.example.teachingapp.Application
import com.example.teachingapp.R
import com.example.teachingapp.data.model.viewmodel.MainViewModel
import com.example.teachingapp.data.model.viewmodel.ViewModelFactory
import com.example.teachingapp.utils.OverLayLoadingManager
import com.example.teachingapp.utils.SharedPrifUtils
import com.example.teachingapp.utils.Status
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_student_profile.*
import kotlinx.android.synthetic.main.activity_teacher_profile.*
import kotlinx.coroutines.runBlocking

private const val TAG = "teacherProfileActivity"

class TeacherProfileActivity : AppCompatActivity() {

	private val teacherProfileViewModel by viewModels<MainViewModel> {
		ViewModelFactory((application as Application).repository)
	}

	private lateinit var sharedPrifUtils: SharedPrifUtils
	private lateinit var auth: FirebaseAuth
	private lateinit var overLayLoadingManager: OverLayLoadingManager

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_teacher_profile)

		overLayLoadingManager = OverLayLoadingManager(this)
		sharedPrifUtils = SharedPrifUtils(this)
		auth = FirebaseAuth.getInstance()

		runBlocking {
			getUserProfile(auth.currentUser?.email.toString())
		}

	}

	private fun getUserProfile(email: String) {
		teacherProfileViewModel.getSingleUser(email).asLiveData().observe(this) {
			Log.d(TAG, "getUserProfile: get user profile response: ${it.status}")
			when (it.status) {
				Status.LOADING -> {
					overLayLoadingManager.show()
				}
				Status.SUCCESS -> {
					overLayLoadingManager.dismiss()

					val value = it.data
					tprofile_user_id.text = value?.id.toString() ?: "N/A"
					id_tprofile_name_top.text = value?.name.toString() ?: "N/A"
					id_tprofile_name.text = value?.name.toString() ?: "N/A"
					id_tprofile_email.text = value?.email.toString() ?: "N/A"
					id_tprofile_department.text = value?.dept.toString() ?: "N/A"
					id_tprofile_mobile.text = value?.mobile.toString() ?: "N/A"
					id_tprofile_address.text = value?.address.toString() ?: "N/A"
//					id_join_date.text =
//						"Joining Date: ${value?.registerDate.toString()}" ?: "Joining Date: N/A"

					Log.d(TAG, "getUserProfile: success: $value")
				}
				Status.ERROR -> {
					overLayLoadingManager.dismiss()
					Log.d(TAG, "getUserProfile: error: ${it.message}")
				}
				null -> {
					overLayLoadingManager.dismiss()
					Log.d(TAG, "getUserProfile: null!!!")
				}
			}
		}
	}

}