package com.example.teachingapp.ui.users

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

private const val TAG = "allUsersActivity"

class AllUsersActivity : AppCompatActivity() {

	private val allUsersViewModel by viewModels<MainViewModel> {
		ViewModelFactory((application as Application).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_all_users)

		window.statusBarColor = resources.getColor(R.color.blue)

		runBlocking {
			getAllUsers()
		}

	}

	private suspend fun getAllUsers() {
		allUsersViewModel.getAllUsers().asLiveData().observe(this) {
			Log.d(TAG, "getAllUsers: get all users response: ${it.status}")

			when (it.status) {
				Status.LOADING -> {
					Log.d(TAG, "getAllUsers: Loading...")
					Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
				}
				Status.SUCCESS -> {
					Log.d(TAG, "getAllUsers: success: ${it.data}")
					Toast.makeText(this, "${it.data}", Toast.LENGTH_SHORT).show()
				}
				Status.ERROR -> {
					Log.d(TAG, "getAllUsers: error: ${it.message}")
					Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
				}
				null -> {
					Log.d(TAG, "getAllUsers: null!!!")
				}
			}
		}
	}

}