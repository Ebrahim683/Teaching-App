package com.example.teachingapp.ui.profile

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import androidx.lifecycle.asLiveData
import com.example.teachingapp.Application
import com.example.teachingapp.R
import com.example.teachingapp.data.model.datamodel.updateprofile.UpdateProfileModel
import com.example.teachingapp.data.model.viewmodel.MainViewModel
import com.example.teachingapp.data.model.viewmodel.ViewModelFactory
import com.example.teachingapp.utils.OverLayLoadingManager
import com.example.teachingapp.utils.Status
import com.google.android.material.textfield.TextInputLayout
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

	private lateinit var updateNameEt: TextInputLayout
	private lateinit var updateMobileEt: TextInputLayout
	private lateinit var updateAddressEt: TextInputLayout

	private val updateViewModel by viewModels<MainViewModel> {
		ViewModelFactory((application as Application).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_update_profile)

		role = intent.getStringExtra("role").toString()
		if (role == "student") {
			window.statusBarColor = resources.getColor(R.color.student_color)
			supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.student_color)))
			id_btn_save.setBackgroundColor(resources.getColor(R.color.student_color))
		} else if (role == "teacher") {
			window.statusBarColor = resources.getColor(R.color.blue)
			supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.blue)))
			id_btn_save.setBackgroundColor(resources.getColor(R.color.blue))
		}

		overLayLoadingManager = OverLayLoadingManager(this)

		updateNameEt = findViewById(R.id.id_update_profile_name)
		updateMobileEt = findViewById(R.id.id_update_profile_mobile)
		updateAddressEt = findViewById(R.id.id_update_profile_address)

		id = intent.getStringExtra("id").toString()
		name = intent.getStringExtra("name").toString()
		email = intent.getStringExtra("email").toString()
		dept = intent.getStringExtra("dept").toString()
		mobile = intent.getStringExtra("mobile").toString()
		address = intent.getStringExtra("address").toString()

		Log.d(TAG, "onCreate: $role")

		update_profile_user_id.text = id
		id_update_profile_name_top.text = name
		id_update_profile_email.text = email
		id_update_profile_department.text = dept

		updateNameEt.editText?.setText(name)
		updateMobileEt.editText?.setText(mobile)
		updateAddressEt.editText?.setText(address)


		id_btn_save.setOnClickListener {

			if (updateNameEt.isNotEmpty() && updateMobileEt.isNotEmpty() && updateAddressEt.isNotEmpty()) {
				val updatedName = updateNameEt.editText?.text.toString()
				val updatedMobile = updateMobileEt.editText?.text.toString()
				val updatedAddress = updateAddressEt.editText?.text.toString()

				updateProfile(
					email,
					UpdateProfileModel(
						updatedName,
						updatedMobile,
						updatedAddress
					)
				)
				Log.d(TAG, "onCreate: $updatedName/$updatedMobile/$updatedAddress")

			} else {
				Toast.makeText(this, "Please fill the form to update profile", Toast.LENGTH_SHORT)
					.show()
			}
		}

	}

	private fun updateProfile(email: String, updateProfileModel: UpdateProfileModel) {
		updateViewModel.updateProfile(email, updateProfileModel)
			.asLiveData()
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
							startActivity(Intent(this, ProfileActivity::class.java))
							finish()
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