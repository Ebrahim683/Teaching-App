package com.example.teachingapp.ui.profile

import android.content.Intent
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
import com.example.teachingapp.utils.OverLayLoadingManager
import com.example.teachingapp.utils.SharedPrifUtils
import com.example.teachingapp.utils.Status
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_student_profile.*
import kotlinx.coroutines.runBlocking

private const val TAG = "studentProfileActivity"

class StudentProfileActivity : AppCompatActivity() {

	private val studentProfileViewModel by viewModels<MainViewModel> {
		ViewModelFactory((application as Application).repository)
	}

	private lateinit var sharedPrifUtils: SharedPrifUtils
	private lateinit var auth: FirebaseAuth
	private lateinit var overLayLoadingManager: OverLayLoadingManager


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_student_profile)

		overLayLoadingManager = OverLayLoadingManager(this)
		sharedPrifUtils = SharedPrifUtils(this)
		auth = FirebaseAuth.getInstance()
		runBlocking {
			getUserProfile(auth.currentUser?.email.toString())
//			getSingleStudentResult()
		}

	}

	private fun getUserProfile(email: String) {
		studentProfileViewModel.getSingleUser(email).asLiveData().observe(this) {
			Log.d(TAG, "getUserProfile: get user profile response: ${it.status}")
			when (it.status) {
				Status.LOADING -> {
					overLayLoadingManager.show()
				}
				Status.SUCCESS -> {
					overLayLoadingManager.dismiss()

					val value = it.data
					profile_user_id.text = value?.id.toString() ?: "N/A"
					id_profile_name_top.text = value?.name.toString() ?: "N/A"
					id_profile_name.text = value?.name.toString() ?: "N/A"
					id_profile_email.text = value?.email.toString() ?: "N/A"
					id_profile_department.text = value?.dept.toString() ?: "N/A"
					id_profile_mobile.text = value?.mobile.toString() ?: "N/A"
					id_profile_address.text = value?.address.toString() ?: "N/A"
//					id_join_date.text =
//						"Joining Date: ${value?.registerDate.toString()}" ?: "Joining Date: N/A"

					Log.d(TAG, "getUserProfile: success: $value")

					id_btn_update.setOnClickListener {
						val intent = Intent(this, UpdateProfileActivity::class.java)
						intent.apply {
							putExtra("id", value?.id.toString())
							putExtra("name", value?.name.toString())
							putExtra("email", value?.email.toString())
							putExtra("dept", value?.dept.toString())
							putExtra("mobile", value?.mobile.toString())
							putExtra("address", value?.address.toString())
							putExtra("role", value?.role.toString())
						}
						startActivity(intent)
					}

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