package com.example.teachingapp.ui.attendance

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teachingapp.Application
import com.example.teachingapp.R
import com.example.teachingapp.data.model.datamodel.attendance.SingleStudentAttModel
import com.example.teachingapp.data.model.viewmodel.MainViewModel
import com.example.teachingapp.data.model.viewmodel.ViewModelFactory
import com.example.teachingapp.utils.OverLayLoadingManager
import com.example.teachingapp.utils.Status
import kotlinx.android.synthetic.main.activity_attendance_student_list.*

private const val TAG = "attendanceStudentListActivity"

class AttendanceStudentListActivity : AppCompatActivity() {

	private lateinit var courseId: String
	private lateinit var date: String
	private lateinit var overLayLoadingManager: OverLayLoadingManager
	private lateinit var mAdapter: AttendanceStudentListAdapter
	private lateinit var arrayList: ArrayList<SingleStudentAttModel>

	private val attendanceStudentListViewModel by viewModels<MainViewModel> {
		ViewModelFactory((application as Application).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_attendance_student_list)

		overLayLoadingManager = OverLayLoadingManager(this)
		mAdapter = AttendanceStudentListAdapter()
		arrayList = ArrayList()

		courseId = intent.getStringExtra("courseId").toString()
		date = intent.getStringExtra("date").toString()

		att_course_id_st.text = "Course ID: $courseId"
		att_course_date_st.text = "Date: $date"
		Log.d(TAG, "onCreate: $courseId/$date")

		getAttendanceByDate(courseId, date)

	}

	private fun getAttendanceByDate(courseId: String, date: String) {
		attendanceStudentListViewModel.getStudentAttendanceByDate(courseId, date).asLiveData()
			.observe(this) {
				Log.d(TAG, "getAttendanceByDate: attendance by date response ${it.status}")

				when (it.status) {
					Status.LOADING -> {
						overLayLoadingManager.show()
					}
					Status.SUCCESS -> {
						overLayLoadingManager.dismiss()
						val data = it.data
						Log.d(TAG, "getAttendanceByDate: success $data")

						data!!.forEach {
							for (i in 0..it.stuList!!.size) {
								val singleStudentAttModel =
									SingleStudentAttModel(it.stuList.get(i).toString())
								arrayList.add(singleStudentAttModel)
								mAdapter.submitList(arrayList)
								mAdapter.notifyDataSetChanged()
								rec_id_att_st_list_course_id.apply {
									setHasFixedSize(true)
									layoutManager =
										LinearLayoutManager(this@AttendanceStudentListActivity)
									adapter = mAdapter
								}
							}
						}

					}
					Status.ERROR -> {
						overLayLoadingManager.dismiss()
						Log.d(TAG, "getAttendanceByDate: error ${it.message}")
						Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
					}
					null -> {
						overLayLoadingManager.dismiss()
						Log.d(TAG, "getAttendanceByDate: null!!")
					}
				}
			}
	}

}