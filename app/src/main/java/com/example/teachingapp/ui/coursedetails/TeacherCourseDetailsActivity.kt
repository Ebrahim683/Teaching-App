package com.example.teachingapp.ui.coursedetails

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teachingapp.Application
import com.example.teachingapp.R
import com.example.teachingapp.data.model.datamodel.coursemodel.TeacherCourseDetailsModel
import com.example.teachingapp.data.model.viewmodel.MainViewModel
import com.example.teachingapp.data.model.viewmodel.ViewModelFactory
import com.example.teachingapp.utils.OverLayLoadingManager
import com.example.teachingapp.utils.Status
import kotlinx.android.synthetic.main.activity_teacher_course_details.*


private const val TAG = "teacherCourseDetailsActivity"

class TeacherCourseDetailsActivity : AppCompatActivity() {

	private lateinit var studentList: ArrayList<String>
	private lateinit var courseId: String
	private lateinit var courseTitle: String
	private lateinit var mAdapter: TeacherCourseDetailsAdapter
	private lateinit var arrayList: ArrayList<TeacherCourseDetailsModel>
	private lateinit var overLayLoadingManager: OverLayLoadingManager
	private val studentCoursesViewModel by viewModels<MainViewModel> {
		ViewModelFactory((application as Application).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_teacher_course_details)

		overLayLoadingManager = OverLayLoadingManager(this)
		mAdapter = TeacherCourseDetailsAdapter()
		arrayList = ArrayList()

		studentList = intent.getStringArrayListExtra("studentList") as ArrayList<String>
		courseId = intent.getStringExtra("courseID").toString()
		courseTitle = intent.getStringExtra("courseTitle").toString()

		sample_enrolled_course_id.text = "Course ID: $courseId"
		sample_enrolled_course_title.text = "Course Title: $courseTitle"

		try {
			for (i in 0..studentList.size) {
				getUserProfile(studentList[i])
			}
		}catch (e:Exception){
			Log.d(TAG, "onCreate: ${e.message}")
		}


	}

	private fun getUserProfile(email: String) {
		studentCoursesViewModel.getSingleUser(email).asLiveData().observe(this) {
			Log.d(TAG, "getUserProfile: get student profile response: ${it.status}")
			when (it.status) {
				Status.LOADING -> {
					overLayLoadingManager.show()
				}
				Status.SUCCESS -> {
					overLayLoadingManager.dismiss()
					val value = it.data

					arrayList.add(
						TeacherCourseDetailsModel(
							value?.name,
							value?.id,
							email
						)
					)
					mAdapter.submitList(arrayList)
					mAdapter.notifyDataSetChanged()
					enrolled_st_rec.apply {
						setHasFixedSize(true)
						layoutManager = LinearLayoutManager(this@TeacherCourseDetailsActivity)
						adapter = mAdapter
					}

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
