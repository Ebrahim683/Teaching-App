package com.example.teachingapp.ui.result

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import com.example.teachingapp.Application
import com.example.teachingapp.R
import com.example.teachingapp.data.model.viewmodel.MainViewModel
import com.example.teachingapp.data.model.viewmodel.ViewModelFactory
import com.example.teachingapp.utils.Status
import kotlinx.coroutines.runBlocking

private const val TAG = "allStudentsResultActivity"
class AllStudentsResultActivity : AppCompatActivity() {

	private val allStudentsResultViewModel by viewModels<MainViewModel> {
		ViewModelFactory((application as Application).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_all_students_result)

		runBlocking {
			getAllStudentsResult()
		}

	}

	@SuppressLint("LongLogTag")
	private suspend fun getAllStudentsResult() {
		allStudentsResultViewModel.getAllStudentsResult().asLiveData().observe(this){
			Log.d(TAG, "getAllStudentsResult: all students result response ${it.status}")

			when(it.status){
				Status.LOADING -> {
					Log.d(TAG, "getAllStudentsResult: Loading...")
					Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
				}
				Status.SUCCESS -> {
					Log.d(TAG, "getAllStudentsResult: success: ${it.data}")
					Toast.makeText(this, "${it.data}", Toast.LENGTH_SHORT).show()
				}
				Status.ERROR -> {
					Log.d(TAG, "getAllStudentsResult: error: ${it.message}")
					Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
				}
				null -> {
					Log.d(TAG, "getAllStudentsResult: null!!!")
				}
			}
		}
	}
}