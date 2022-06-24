package com.example.teachingapp.ui.dashboard.studentdashboard

import android.content.Intent
import android.graphics.drawable.ColorDrawable
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.teachingapp.Application
import com.example.teachingapp.R
import com.example.teachingapp.data.model.datamodel.coursemodel.CoursesModel
import com.example.teachingapp.data.model.datamodel.teachermodel.TeacherCourseModel
import com.example.teachingapp.data.model.viewmodel.MainViewModel
import com.example.teachingapp.data.model.viewmodel.ViewModelFactory
import com.example.teachingapp.ui.auth.LoginActivity
import com.example.teachingapp.ui.balance.BalanceActivity
import com.example.teachingapp.ui.course.CoursesActivity
import com.example.teachingapp.ui.main.MainActivity
import com.example.teachingapp.ui.profile.StudentProfileActivity
import com.example.teachingapp.ui.result.ResultActivity
import com.example.teachingapp.utils.CourseItemCLickListener
import com.example.teachingapp.utils.OverLayLoadingManager
import com.example.teachingapp.utils.SharedPrifUtils
import com.example.teachingapp.utils.Status
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_student_dashboard.*

private const val TAG = "studentDashboardActivity"

class StudentDashboardActivity : AppCompatActivity() {

	private lateinit var sharedPrifUtils: SharedPrifUtils
	private lateinit var studentDashboardAdapter: StudentDashboardAdapter
	private lateinit var studentArrayList: ArrayList<CoursesModel>
	private lateinit var overLayLoadingManager: OverLayLoadingManager
	private lateinit var auth: FirebaseAuth
	private lateinit var email: String
	private lateinit var swipeRefreshLayout: SwipeRefreshLayout

	private val studentDashboardViewModel by viewModels<MainViewModel> {
		ViewModelFactory((application as Application).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_student_dashboard)

		supportActionBar?.setDisplayShowHomeEnabled(true)
		window.statusBarColor = resources.getColor(R.color.student_color)
		supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.student_color)))

		swipeRefreshLayout = findViewById(R.id.id_refresh_student_dashboard)

		sharedPrifUtils = SharedPrifUtils(this)
		studentArrayList = ArrayList()
		studentDashboardAdapter = StudentDashboardAdapter()
		studentDashboardAdapter.setContext(this)
		auth = FirebaseAuth.getInstance()
		email = auth.currentUser?.email.toString()

		overLayLoadingManager = OverLayLoadingManager(this)

		getUserProfile(email)

		swipeRefreshLayout.setOnRefreshListener {
			studentArrayList.clear()
			getUserProfile(email)
		}

		id_welcome_text.text = "WELCOME BACK! "
		btn_total_paid.text = "Total Paid"
		btn_balance.text = "Total Balance"

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

//			R.id.menu_all_students_result -> {
//
//				startActivity(Intent(this, AllStudentsResultActivity::class.java))
//			}

			R.id.menu_student_logout -> {
				sharedPrifUtils.logOut()
				startActivity(Intent(this, LoginActivity::class.java))
				finish()
			}

		}
		return super.onOptionsItemSelected(item)
	}

	private fun getUserProfile(email: String) {
		studentDashboardViewModel.getSingleUser(email).asLiveData()
			.observe(this) {
				Log.d(TAG, "getUserProfile: get user profile response: ${it.status}")
				when (it.status) {
					Status.LOADING -> {
						overLayLoadingManager.show()
					}
					Status.SUCCESS -> {
						overLayLoadingManager.dismiss()
						val value = it.data

						id_name_text.text = it.data?.name ?: "Student"
						btn_total_paid.text = "Total Paid\n1432.32"
						btn_balance.text = "Total Balance\n${value?.balance}"
						Log.d(TAG, "getUserProfile: success: $value")

						val courseCode = value?.courses
						for (i in 0..courseCode?.size!!) {
							getEnrolledCourses("${courseCode.get(i)}")
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


	private fun getEnrolledCourses(courseID: String) {
		studentDashboardViewModel.showEnrolledCourses(courseID).asLiveData().observe(this) {
			Log.d(TAG, "getEnrolledCourses: enrolled courses response ${it.status}")

			when (it.status) {
				Status.LOADING -> {
					overLayLoadingManager.show()
				}
				Status.SUCCESS -> {
					overLayLoadingManager.dismiss()
					val data = it.data
					Log.d(TAG, "getEnrolledCourses: $data")
					studentArrayList.add(
						CoursesModel(
							data?._id,
							data?.courseId,
							data?.courseTitle,
							data?.courseImg
						)
					)
					studentDashboardAdapter.submitList(studentArrayList)
					studentDashboardAdapter.notifyDataSetChanged()

					id_rec_student_dashboard.apply {
						setHasFixedSize(true)
						layoutManager = GridLayoutManager(this@StudentDashboardActivity, 2)
						adapter = studentDashboardAdapter
					}

					studentDashboardAdapter.studentItemClick(object : CourseItemCLickListener {
						override fun onStudentItemClick(
							view: View,
							position: Int,
							coursesModel: CoursesModel
						) {
							val intent =
								Intent(this@StudentDashboardActivity, MainActivity::class.java)
							intent.apply {
								putExtra("title", coursesModel.courseTitle)
							}
							startActivity(intent)
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
					stopRefresh()
				}
				Status.ERROR -> {
					overLayLoadingManager.dismiss()
//					Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
				}
				null -> {
					overLayLoadingManager.dismiss()
				}
			}
		}
	}

	private fun stopRefresh() {
		if (swipeRefreshLayout.isRefreshing) {
			swipeRefreshLayout.isRefreshing = false
		}
	}

}