package com.example.teachingapp.ui.dashboard.teacherdashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teachingapp.Application
import com.example.teachingapp.R
import com.example.teachingapp.data.model.datamodel.coursemodel.CoursesModel
import com.example.teachingapp.data.model.datamodel.teachermodel.TeacherCourseModel
import com.example.teachingapp.data.model.viewmodel.MainViewModel
import com.example.teachingapp.data.model.viewmodel.ViewModelFactory
import com.example.teachingapp.ui.attendance.AttendanceCourseIdActivity
import com.example.teachingapp.ui.auth.LoginActivity
import com.example.teachingapp.ui.balance.BalanceActivity
import com.example.teachingapp.ui.course.CoursesActivity
import com.example.teachingapp.ui.coursedetails.TeacherCourseDetailsActivity
import com.example.teachingapp.ui.profile.StudentProfileActivity
import com.example.teachingapp.ui.result.AllStudentsResultActivity
import com.example.teachingapp.ui.result.ResultActivity
import com.example.teachingapp.utils.CourseItemCLickListener
import com.example.teachingapp.utils.OverLayLoadingManager
import com.example.teachingapp.utils.SharedPrifUtils
import com.example.teachingapp.utils.Status
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_teacher_dashboard.*

private const val TAG = "teacherDashboardActivity"

class TeacherDashboardActivity : AppCompatActivity() {

	private lateinit var sharedPrifUtils: SharedPrifUtils
	private lateinit var teacherDashboardAdapter: TeacherDashboardAdapter
	private lateinit var teacherArrayList: ArrayList<TeacherCourseModel>
	private lateinit var overLayLoadingManager: OverLayLoadingManager
	private lateinit var auth: FirebaseAuth
	private lateinit var email: String
	private lateinit var recyclerView: RecyclerView

	private val teacherDashboardViewModel by viewModels<MainViewModel> {
		ViewModelFactory((application as Application).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_teacher_dashboard)

		supportActionBar?.setDisplayShowHomeEnabled(true)

		recyclerView = findViewById(R.id.id_teacher_rec_dashboard)
		sharedPrifUtils = SharedPrifUtils(this)
		teacherArrayList = ArrayList()
		teacherDashboardAdapter = TeacherDashboardAdapter()
		auth = FirebaseAuth.getInstance()
		email = auth.currentUser?.email.toString()

		overLayLoadingManager = OverLayLoadingManager(this)

		id_teacher_welcome_text.text = "WELCOME BACK! "
		btn_teacher_total_paid.text = "Total Paid"
		btn_teacher_balance.text = "Total Balance"

		getUserProfile(email)
	}


	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.student_dashboard_menu, menu)
		return super.onCreateOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {

		when (item.itemId) {
			R.id.menu_student_profile -> {
				startActivity(Intent(this, StudentProfileActivity::class.java))
			}

			R.id.menu_student_courses -> {
				startActivity(Intent(this, CoursesActivity::class.java))
			}

			R.id.menu_student_result -> {
				val intent = Intent(this, ResultActivity::class.java)
				intent.apply {
					putExtra("email", email)
				}
				startActivity(intent)
			}

			R.id.menu_student_balance -> {
				startActivity(Intent(this, BalanceActivity::class.java))
			}

//			R.id.menu_all_users -> {
//				startActivity(Intent(this, AllUsersActivity::class.java))
//			}

			R.id.menu_all_students_result -> {
				startActivity(Intent(this, AllStudentsResultActivity::class.java))
			}

			R.id.menu_student_logout -> {
				sharedPrifUtils.logOut()
				startActivity(Intent(this, LoginActivity::class.java))
				finish()
			}

		}
		return super.onOptionsItemSelected(item)
	}

	private fun getUserProfile(email: String) {
		teacherDashboardViewModel.getSingleUser(email).asLiveData()
			.observe(this) {
				Log.d(TAG, "getUserProfile: get user profile response: ${it.status}")
				when (it.status) {
					Status.LOADING -> {
						overLayLoadingManager.show()
					}
					Status.SUCCESS -> {
						overLayLoadingManager.dismiss()
						val value = it.data
						id_teacher_name_text.text = it.data?.name ?: "Teacher"
						btn_teacher_total_paid.text = "Total Paid\n1432.32"
						btn_teacher_balance.text = "Total Balance\n${value?.balance}"
						Log.d(TAG, "getUserProfile: success: $value")

						val courses = value?.courses
						for (i in 0..courses?.size!!) {
							getTeacherCourses("${courses[i]}")
						}
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

	private fun getTeacherCourses(courseId: String) {
		teacherDashboardViewModel.showTeacherCourses(courseId).asLiveData().observe(this) { it ->
			Log.d(TAG, "getTeacherCourses: teacher courses response ${it.status}")

			when (it.status) {
				Status.LOADING -> {
					overLayLoadingManager.show()
				}
				Status.SUCCESS -> {
					overLayLoadingManager.dismiss()
					val data = it.data

					teacherArrayList.add(
						TeacherCourseModel(
							data?._id,
							data?.courseId,
							data?.courseTitle,
							data?.courseImg,
							data?.studentList
						)
					)
					teacherDashboardAdapter.submitList(teacherArrayList)
					teacherDashboardAdapter.notifyDataSetChanged()
					recyclerView.apply {
						setHasFixedSize(true)
						layoutManager = GridLayoutManager(this@TeacherDashboardActivity, 2)
						adapter = teacherDashboardAdapter
					}

					teacherDashboardAdapter.teacherCourseItemClick(object :
						CourseItemCLickListener {
						override fun onStudentItemClick(
							view: View,
							position: Int,
							coursesModel: CoursesModel
						) {

						}

						override fun onTeacherItemClick(
							view: View,
							position: Int,
							teacherCourseModel: TeacherCourseModel
						) {
							val intent = Intent(
								this@TeacherDashboardActivity,
								TeacherCourseDetailsActivity::class.java
							)
							intent.apply {
								putStringArrayListExtra(
									"studentList",
									teacherCourseModel.studentList as java.util.ArrayList<String>
								)
								putExtra("courseID", teacherCourseModel.courseId)
								putExtra("courseTitle", teacherCourseModel.courseTitle)
							}
							startActivity(intent)
						}

						override fun onAttendanceItemClick(
							view: View,
							position: Int,
							teacherCourseModel: TeacherCourseModel
						) {
							val intent = Intent(
								this@TeacherDashboardActivity,
								AttendanceCourseIdActivity::class.java
							)
							intent.putExtra("courseId", teacherCourseModel.courseId)
							startActivity(intent)
						}
					})

					Log.d(TAG, "getTeacherCourses: $data")
				}
				Status.ERROR -> {
					overLayLoadingManager.dismiss()
					Log.d(TAG, "getTeacherCourses: error ${it.message}")
					Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
				}
				null -> {
					overLayLoadingManager.dismiss()
				}
			}
		}
	}

}