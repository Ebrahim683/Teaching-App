package com.example.teachingapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.example.teachingapp.Application
import com.example.teachingapp.R
import com.example.teachingapp.data.model.datamodel.usermodel.AllUsersModelItem
import com.example.teachingapp.data.model.viewmodel.MainViewModel
import com.example.teachingapp.data.model.viewmodel.ViewModelFactory
import com.example.teachingapp.ui.dashboard.studentdashboard.StudentDashboardActivity
import com.example.teachingapp.ui.dashboard.teacherdashboard.TeacherDashboardActivity
import com.example.teachingapp.utils.OverLayLoadingManager
import com.example.teachingapp.utils.SharedPrifUtils
import com.example.teachingapp.utils.Status
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.runBlocking

private const val TAG = "loginActivity"

class LoginActivity : AppCompatActivity() {

	private val loginViewModel by viewModels<MainViewModel> {
		ViewModelFactory((application as Application).repository)
	}

	private lateinit var mAuth: FirebaseAuth
	private lateinit var email: String
	private lateinit var password: String
	private lateinit var sharedPrifUtils: SharedPrifUtils
	private lateinit var arrayList: ArrayList<AllUsersModelItem>
	private lateinit var overLayLoadingManager: OverLayLoadingManager

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)

		overLayLoadingManager = OverLayLoadingManager(this)
		sharedPrifUtils = SharedPrifUtils(this)
		mAuth = FirebaseAuth.getInstance()

		arrayList = ArrayList()

		id_tv_signup.setOnClickListener {
			startActivity(Intent(this, SignUpActivity::class.java))
			finish()
		}

		id_btn_login.setOnClickListener {
			runBlocking {
				signInUser()
			}
		}

		id_forgot_pass.setOnClickListener {
			forgotPassword()
		}

		if (sharedPrifUtils.isLoggedIn()) {
			if (sharedPrifUtils.getUserRole() == "student") {
				startActivity(Intent(this, StudentDashboardActivity::class.java))
				finish()
			} else {
				startActivity(Intent(this, TeacherDashboardActivity::class.java))
				finish()
			}
		}

	}

	private fun signInUser() {
		email = id_email.editText!!.text.toString().trim()
		password = id_password.editText!!.text.toString().trim()

		if (email.isEmpty()) {
			id_email.editText!!.error = "Enter valid email"
		} else if (password.isEmpty()) {
			id_password.editText!!.error = "Enter password"
		} else {
			overLayLoadingManager.show()
			mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
				if (task.isSuccessful) {
					runBlocking {
						getAllUsers(email)
					}

				} else {
					overLayLoadingManager.dismiss()
					Toast.makeText(this, "${task.exception?.localizedMessage}", Toast.LENGTH_SHORT)
						.show()
					Log.d(TAG, "signInUser: ${task.exception?.localizedMessage}")
				}
			}
		}
	}

	private fun forgotPassword() {
		email = id_email.editText!!.text.toString().trim()
		if (email.isEmpty()) {
			id_email.editText!!.error = "Enter valid email"
		} else {
			overLayLoadingManager.show()
			mAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
				if (task.isSuccessful) {
					overLayLoadingManager.dismiss()
					Toast.makeText(this, "Reset link sent to your mail", Toast.LENGTH_SHORT).show()
					Log.d(TAG, "forgotPasswordSuccess: ${task.result}")
				} else {
					overLayLoadingManager.dismiss()
					Toast.makeText(this, task.exception!!.localizedMessage, Toast.LENGTH_SHORT)
						.show()
					Log.d(TAG, "forgotPasswordFailed: ${task.exception!!.localizedMessage}")
				}
			}
		}

	}

	private fun getAllUsers(email: String) {
		loginViewModel.getAllUsers().asLiveData().observe(this) {
			Log.d(TAG, "getAllUsers: get all users response: ${it.status}")

			when (it.status) {
				Status.LOADING -> {
					Log.d(TAG, "getAllUsers: Loading...")
					overLayLoadingManager.show()
				}
				Status.SUCCESS -> {
					it.data?.let { allUserList ->
						arrayList.addAll(allUserList)
					}
					Log.d(TAG, "getAllUsers: $arrayList")
					val userListForFetch = ArrayList<AllUsersModelItem>()
					userListForFetch.addAll(arrayList)
					userListForFetch.forEach { filteredList ->
						if (filteredList.email == email) {
							if (filteredList.role == "student") {
								Log.d(TAG, "getAllUsers: Student Login")
								sharedPrifUtils.saveUser(
									role = filteredList.role.toString()
								)
								overLayLoadingManager.dismiss()
								startActivity(Intent(this, StudentDashboardActivity::class.java))
								finish()
							} else if (filteredList.role == "teacher") {
								Log.d(TAG, "getAllUsers: Teacher Login")
								sharedPrifUtils.saveUser(
									role = filteredList.role.toString()
								)
								overLayLoadingManager.dismiss()
								startActivity(Intent(this, TeacherDashboardActivity::class.java))
								finish()
							}
						}
					}
				}
				Status.ERROR -> {
					overLayLoadingManager.dismiss()
					Log.d(TAG, "getAllUsers: error: ${it.message}")
					Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
				}
				null -> {
					overLayLoadingManager.dismiss()
					Log.d(TAG, "getAllUsers: null!!!")
				}
			}
		}
	}
}