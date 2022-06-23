package com.example.teachingapp.ui.attendance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.teachingapp.Application
import com.example.teachingapp.R
import com.example.teachingapp.data.model.datamodel.attendance.AttendanceCourseModel
import com.example.teachingapp.data.model.viewmodel.MainViewModel
import com.example.teachingapp.data.model.viewmodel.ViewModelFactory
import com.example.teachingapp.utils.AttendanceClickListener
import com.example.teachingapp.utils.OverLayLoadingManager
import com.example.teachingapp.utils.Status
import kotlinx.android.synthetic.main.activity_attendance_course_id.*

private const val TAG = "attendanceCourseIdActivity"

class AttendanceCourseIdActivity : AppCompatActivity() {

	private lateinit var courseId: String
	private lateinit var overLayLoadingManager: OverLayLoadingManager
	private lateinit var mAdapter: AttendanceCourseIdAdapter
	private lateinit var arrayList: ArrayList<AttendanceCourseModel>

	private val attendanceCourseIdViewModel by viewModels<MainViewModel> {
		ViewModelFactory((application as Application).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_attendance_course_id)

		overLayLoadingManager = OverLayLoadingManager(this)
		mAdapter = AttendanceCourseIdAdapter()
		arrayList = ArrayList()

		courseId = intent.getStringExtra("courseId").toString()
		att_course_id.text = "Course ID: $courseId"
		attendanceCourseId(courseId)

	}

	private fun attendanceCourseId(courseId: String) {
		attendanceCourseIdViewModel.getStudentAttendance(courseId).asLiveData().observe(this) {
			Log.d(TAG, "getUserProfile: get user profile response: ${it.status}")
			when (it.status) {
				Status.LOADING -> {
					overLayLoadingManager.show()
				}
				Status.SUCCESS -> {
					overLayLoadingManager.dismiss()
					val data = it.data

					mAdapter.submitList(data)
					mAdapter.notifyDataSetChanged()
					rec_id_att_course_id.apply {
						setHasFixedSize(true)
						layoutManager = GridLayoutManager(this@AttendanceCourseIdActivity, 2)
						adapter = mAdapter
					}

					mAdapter.onDateClick(object : AttendanceClickListener {
						override fun onDateClick(
							view: View,
							position: Int,
							attendanceCourseModel: AttendanceCourseModel
						) {
							val intent = Intent(
								this@AttendanceCourseIdActivity,
								AttendanceStudentListActivity::class.java
							)
							intent.apply {
								putExtra("courseId", courseId)
								putExtra("date", attendanceCourseModel.date)
							}
							startActivity(intent)
							Log.d(TAG, "onDateClick: Date: ${attendanceCourseModel.date}")
						}
					})
					Log.d(TAG, "attendanceCourseId: $data")
				}
				Status.ERROR -> {
					overLayLoadingManager.dismiss()
					Log.d(TAG, "getUserProfile: error: ${it.message}")
					Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
				}
				null -> {
					overLayLoadingManager.dismiss()
					Log.d(TAG, "getUserProfile: null!!!")
				}
			}
		}
	}

}