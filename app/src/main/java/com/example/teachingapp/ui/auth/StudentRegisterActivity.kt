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
import com.example.teachingapp.data.model.datamodel.studentmodel.StudentRegistrationModel
import com.example.teachingapp.data.model.viewmodel.MainViewModel
import com.example.teachingapp.data.model.viewmodel.ViewModelFactory
import com.example.teachingapp.utils.OverLayLoadingManager
import com.example.teachingapp.utils.Status
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_student_register.*
import kotlinx.coroutines.runBlocking
import java.util.*


private const val TAG = "studentRegisterActivity"
private const val role = "student"

class StudentRegisterActivity : AppCompatActivity() {

	private lateinit var sAuth: FirebaseAuth
	private lateinit var name: String
	private lateinit var email: String
	private lateinit var password: String
	private lateinit var confirmPassword: String
	private lateinit var level: String
	private lateinit var id: String
	private lateinit var dept: String
	private lateinit var mobile: String
	private lateinit var address: String
	private lateinit var parentsContact: String
	private lateinit var balance: String
	private lateinit var registerDate: String
	private lateinit var updated_date: String
	private lateinit var overLayLoadingManager: OverLayLoadingManager

	private val studentRegistrationModel by viewModels<MainViewModel> {
		ViewModelFactory((application as Application).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_student_register)

		overLayLoadingManager = OverLayLoadingManager(this)

		val calendar = Calendar.getInstance(Locale.getDefault())
		val date = calendar.get(Calendar.DATE)
		val month = calendar.get(Calendar.MONTH) + 1
		val year = calendar.get(Calendar.YEAR)
		registerDate = "$date/$month/$year"
		Log.d(TAG, "onCreate: registerDate: $registerDate")

		updated_date = "$date/$month/$year"
		Log.d(TAG, "onCreate: updated_date: $updated_date")

		id_tv_stlogin.setOnClickListener {
			startActivity(Intent(this, LoginActivity::class.java))
			finish()
		}

		id_btn_student_register.setOnClickListener {
				createStudentAccount()
		}

	}

	private fun createStudentAccount() {
		sAuth = FirebaseAuth.getInstance()
		name = id_name.editText!!.text.toString()
		email = id_email.editText!!.text.toString().trim()
		password = id_password.editText!!.text.toString().trim()
		confirmPassword = id_confirm_password.editText!!.text.toString().trim()
		level = id_level.editText!!.text.toString()
		id = id_user_id.editText!!.text.toString().trim()
		dept = id_dept.editText!!.text.toString()
		mobile = id_mobile.editText!!.text.toString().trim()
		address = id_address.editText!!.text.toString()
		parentsContact = id_parent_contact.editText!!.text.toString().trim()
		balance = id_balance.editText!!.text.toString().trim()

		if (name.isEmpty()) {
			id_name.editText!!.error = "Enter name"
		} else if (email.isEmpty()) {
			id_email.editText!!.error = "Enter email"
		} else if (id.isEmpty()) {
			id_user_id.editText!!.error = "Enter your ID"
		} else if (dept.isEmpty()) {
			id_dept.editText!!.error = "Enter your department"
		} else if (level.isEmpty()) {
			id_level.editText!!.error = "Enter your semester level"
		} else if (mobile.isEmpty()) {
			id_mobile.editText!!.error = "Enter your mobile number"
		} else if (parentsContact.isEmpty()) {
			id_parent_contact.editText!!.error = "Enter parents contact number"
		} else if (address.isEmpty()) {
			id_address.editText!!.error = "Enter your address"
		} else if (balance.isEmpty()) {
			id_balance.editText!!.error = "Enter balance"
		} else if (password.isEmpty()) {
			id_password.editText!!.error = "Enter password"
		} else if (id_confirm_password.editText!!.text.toString().isEmpty()) {
			id_confirm_password.editText!!.error = "Confirm your password"
		} else if (id_confirm_password.editText!!.text.toString() != password) {
			id_confirm_password.editText!!.error = "Password doesn't match"
		} else {
			sAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->

				if (task.isSuccessful) {
					val studentRegisterModel = StudentRegistrationModel(
						role = role,
						id = id,
						name = name,
						email = email,
						dept = dept,
						level = level,
						mobile = mobile,
						address = address,
						balance = balance,
						password = password,
						confirmPass = confirmPassword,
						date = registerDate,
					)

					studentRegistrationModel.studentRegister(studentRegisterModel).asLiveData()
						.observe(this) {
							Log.d(
								TAG,
								"createStudentAccount: student registration response ${it.status}"
							)

							when (it.status) {
								Status.LOADING -> {
									overLayLoadingManager.show()
								}
								Status.SUCCESS -> {
									overLayLoadingManager.dismiss()
									val response = it.data?.acknowledged
									if (response == true) {
										Log.d(
											TAG,
											"createStudentAccount: $response \n${it.data.insertedId}"
										)
										startActivity(Intent(this, LoginActivity::class.java))
										finishAffinity()
									} else {
										Log.d(TAG, "createStudentAccount: ${it.data?.acknowledged}")
									}

								}
								Status.ERROR -> {
									overLayLoadingManager.dismiss()
									Log.d(TAG, "createStudentAccount: ${it.message}")
									Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
								}
								null -> {
									overLayLoadingManager.dismiss()
									Log.d(TAG, "createStudentAccount: null!!!")
									Toast.makeText(this, "null!!!", Toast.LENGTH_SHORT).show()
								}
							}

						}

				} else {
					overLayLoadingManager.dismiss()
					Toast.makeText(this, "${task.exception?.localizedMessage}", Toast.LENGTH_SHORT)
						.show()
					Log.d(TAG, "createStudentAccount: ${task.exception?.localizedMessage}")
				}
			}
		}

	}

}