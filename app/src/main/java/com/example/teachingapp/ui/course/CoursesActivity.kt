package com.example.teachingapp.ui.course

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
import com.example.teachingapp.data.model.datamodel.coursemodel.CoursesModel
import com.example.teachingapp.data.model.datamodel.teachermodel.TeacherCourseModel
import com.example.teachingapp.data.model.viewmodel.MainViewModel
import com.example.teachingapp.data.model.viewmodel.ViewModelFactory
import com.example.teachingapp.ui.dashboard.studentdashboard.StudentDashboardAdapter
import com.example.teachingapp.utils.CourseItemCLickListener
import com.example.teachingapp.utils.OverLayLoadingManager
import com.example.teachingapp.utils.Status
import kotlinx.android.synthetic.main.activity_courses.*
import kotlinx.coroutines.runBlocking

private const val TAG = "coursesActivity"

class CoursesActivity : AppCompatActivity() {

	private val coursesViewMode by viewModels<MainViewModel> {
		ViewModelFactory((application as Application).repository)
	}

	private lateinit var arrayList: ArrayList<CoursesModel>
	private lateinit var studentDashboardAdapter: StudentDashboardAdapter
	private lateinit var overLayLoadingManager: OverLayLoadingManager

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_courses)


		overLayLoadingManager = OverLayLoadingManager(this)
		studentDashboardAdapter = StudentDashboardAdapter()
		studentDashboardAdapter.setContext(this)
		arrayList = ArrayList()

		setUpView()
		runBlocking {
			showCourses()
		}

	}

	private fun setUpView() {
		id_all_courses_rec.apply {
			setHasFixedSize(true)
			layoutManager = GridLayoutManager(this@CoursesActivity, 2)
			adapter = studentDashboardAdapter
		}
	}

	private fun showCourses() {

		coursesViewMode.showCourses().asLiveData().observe(this) {
			Log.d(TAG, "showCourses: show courses response ${it.status}")

			when (it.status) {
				Status.LOADING -> {
					overLayLoadingManager.show()
					Log.d(TAG, "showCourses: Loading...")
				}
				Status.SUCCESS -> {
					overLayLoadingManager.dismiss()
					val data = it.data
					Log.d(TAG, "showCourses: $data")
					studentDashboardAdapter.submitList(data)
					studentDashboardAdapter.notifyDataSetChanged()
					studentDashboardAdapter.studentItemClick(object :
						CourseItemCLickListener {
						override fun onStudentItemClick(
							view: View,
							position: Int,
							coursesModel: CoursesModel
						) {
							Toast.makeText(this@CoursesActivity, "$position", Toast.LENGTH_SHORT)
								.show()
						}

						override fun onTeacherItemClick(
							view: View,
							position: Int,
							teacherCourseModel: TeacherCourseModel
						) {

						}

						override fun onAttendanceItemClick(
							view: View,
							position: Int,
							teacherCourseModel: TeacherCourseModel
						) {

						}
					})
				}
				Status.ERROR -> {
					overLayLoadingManager.dismiss()
					Log.d(TAG, "showCourses: error: ${it.message}")
					Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
				}
				null -> {
					overLayLoadingManager.dismiss()
					Log.d(TAG, "showCourses: null!!!")
				}
			}
		}

	}

}