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
import com.example.teachingapp.data.model.datamodel.updateprofile.UpdateProfileModel
import com.example.teachingapp.data.model.viewmodel.MainViewModel
import com.example.teachingapp.data.model.viewmodel.ViewModelFactory
import com.example.teachingapp.utils.OverLayLoadingManager
import com.example.teachingapp.utils.Status
import kotlinx.android.synthetic.main.activity_update_profile.*


private const val TAG = "updateProfileActivity"

class UpdateProfileActivity : AppCompatActivity() {

	private lateinit var id: String
	private lateinit var name: String
	private lateinit var email: String
	private lateinit var dept: String
	private lateinit var mobile: String
	private lateinit var address: String
	private lateinit var role: String
	private lateinit var overLayLoadingManager: OverLayLoadingManager

	private val updateViewModel by viewModels<MainViewModel> {
		ViewModelFactory((application as Application).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_update_profile)


		overLayLoadingManager = OverLayLoadingManager(this)

		id = intent.getStringExtra("id").toString() ?: "N/A"
		name = intent.getStringExtra("name").toString() ?: "N/A"
		email = intent.getStringExtra("email").toString() ?: "N/A"
		dept = intent.getStringExtra("dept").toString() ?: "N/A"
		mobile = intent.getStringExtra("mobile").toString() ?: "N/A"
		address = intent.getStringExtra("address").toString() ?: "N/A"
		role = intent.getStringExtra("role").toString() ?: "N/A"
		Log.d(TAG, "onCreate: $role")

		if (id != null) {
			update_profile_user_id.text = id
		}
		if (name != null) {
			id_update_profile_name.setText(name)
			id_update_profile_name_top.text = name
		}
		if (email != null) {
			id_update_profile_email.text = email
		}
		if (dept != null) {
			id_update_profile_department.text = dept
		}
		if (mobile != null) {
			id_update_profile_mobile.setText(mobile)
		}
		if (address != null) {
			id_update_profile_address.setText(address)
		}

		id_btn_save.setOnClickListener {
			updateProfile()
		}

	}

	private fun updateProfile() {
		updateViewModel.updateProfile(email, UpdateProfileModel(name, mobile, address)).asLiveData()
			.observe(this) {
				Log.d(TAG, "updateProfile: update profile response ${it.status}")

				when (it.status) {
					Status.LOADING -> {
						overLayLoadingManager.show()
					}
					Status.SUCCESS -> {
						overLayLoadingManager.dismiss()
						val value = it.data
						if (value?.acknowledged == true) {
							Log.d(TAG, "updateProfile: Updated")
							Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()
							if (role == "student") {
								startActivity(Intent(this, StudentProfileActivity::class.java))
								finish()
							} else if (role == "teacher") {
								startActivity(Intent(this, TeacherProfileActivity::class.java))
								finish()
							}
						} else {
							Toast.makeText(
								this,
								"Fail to update profile try again later",
								Toast.LENGTH_SHORT
							).show()
						}
					}
					Status.ERROR -> {
						overLayLoadingManager.dismiss()
						Log.d(TAG, "updateProfile: ${it.message}")
						Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
					}
					null -> {
						overLayLoadingManager.dismiss()
						Log.d(TAG, "updateProfile: null!!!")
					}
				}
			}
	}
}