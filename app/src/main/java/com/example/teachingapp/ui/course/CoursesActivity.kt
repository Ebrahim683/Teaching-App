package com.example.teachingapp.ui.course

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

private const val TAG = "coursesActivity"
class CoursesActivity : AppCompatActivity() {

	private val coursesViewMode by viewModels<MainViewModel> {
		ViewModelFactory((application as Application).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_courses)

		runBlocking {
			showCourses()
		}

	}

	suspend fun showCourses() {

		coursesViewMode.showCourses().asLiveData().observe(this) {
			Log.d(TAG, "showCourses: show courses response ${it.status}")

			when (it.status) {
				Status.LOADING -> {
					Log.d(TAG, "showCourses: Loading...")
					Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
				}
				Status.SUCCESS -> {
					Log.d(TAG, "showCourses: success: ${it.data}")
					Toast.makeText(this, "${it.data}", Toast.LENGTH_SHORT).show()
				}
				Status.ERROR -> {
					Log.d(TAG, "showCourses: error: ${it.message}")
					Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
				}
				null -> {
					Log.d(TAG, "showCourses: null!!!")
				}
			}
		}

	}

}