package com.example.teachingapp.ui.dashboard.studentdashboard

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.teachingapp.R
import com.example.teachingapp.data.model.datamodel.studentmodel.StudentDashBoardModel
import com.example.teachingapp.ui.auth.LoginActivity
import com.example.teachingapp.ui.course.CoursesActivity
import com.example.teachingapp.ui.main.MainActivity
import com.example.teachingapp.ui.profile.StudentProfileActivity
import com.example.teachingapp.ui.result.AllStudentsResultActivity
import com.example.teachingapp.ui.users.AllUsersActivity
import com.example.teachingapp.utils.SharedPrifUtils
import com.example.teachingapp.utils.StudentDashboardItemClickListener
import kotlinx.android.synthetic.main.activity_student_dashboard.*

private const val TAG = "studentDashboardActivity"

class StudentDashboardActivity : AppCompatActivity() {

	private var name: String? = "N/A"
	private var totalPaid: String? = "1432.32"
	private var balance: String? = "1400.12"
	private lateinit var sharedPrifUtils: SharedPrifUtils
	private lateinit var studentDashboardAdapter: StudentDashboardAdapter
	private lateinit var studentArrayList: ArrayList<StudentDashBoardModel>

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_student_dashboard)

		supportActionBar?.setDisplayShowHomeEnabled(true)

		sharedPrifUtils = SharedPrifUtils(this)
		studentArrayList = ArrayList()
		studentDashboardAdapter = StudentDashboardAdapter()

		id_welcome_text.text = "WELCOME BACK! $name"
		btn_total_paid.text = "Total Paid\n$totalPaid"
		btn_balance.text = "Total Paid\n$balance"

		setUpView()

	}

	fun setUpView() {
		studentArrayList.add(
			StudentDashBoardModel(
				R.drawable.img_english.toString(), "English 1st paper"
			)
		)

		studentArrayList.add(
			StudentDashBoardModel(
				R.drawable.img_english.toString(), "English 2nd paper"
			)
		)

		studentArrayList.add(
			StudentDashBoardModel(
				R.drawable.img_english.toString(), "English 3rd paper"
			)
		)

		studentArrayList.add(
			StudentDashBoardModel(
				R.drawable.img_english.toString(), "English 4th paper"
			)
		)

		studentArrayList.add(
			StudentDashBoardModel(
				R.drawable.img_english.toString(), "English 5th paper"
			)
		)

		studentArrayList.add(
			StudentDashBoardModel(
				R.drawable.img_english.toString(), "English 6th paper"
			)
		)

		studentArrayList.add(
			StudentDashBoardModel(
				R.drawable.img_english.toString(), "English 7th paper"
			)
		)

		studentArrayList.add(
			StudentDashBoardModel(
				R.drawable.img_english.toString(), "English 8th paper"
			)
		)

		studentArrayList.add(
			StudentDashBoardModel(
				R.drawable.img_english.toString(), "English 9th paper"
			)
		)

		studentArrayList.add(
			StudentDashBoardModel(
				R.drawable.img_english.toString(), "English 10th paper"
			)
		)

		studentDashboardAdapter.submitList(studentArrayList)

		id_rec_student_dashboard.apply {
			setHasFixedSize(true)
			layoutManager = GridLayoutManager(this@StudentDashboardActivity, 2)
			adapter = studentDashboardAdapter
		}

		studentDashboardAdapter.studentItemClick(object : StudentDashboardItemClickListener {
			override fun onStudentItemClick(
				view: View,
				position: Int,
				studentDashBoardModel: StudentDashBoardModel
			) {
				val intent = Intent(this@StudentDashboardActivity, MainActivity::class.java)
				intent.apply {
					putExtra("title", studentDashBoardModel.title)
				}
				startActivity(intent)
			}
		})
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
				Toast.makeText(this, "Result", Toast.LENGTH_SHORT).show()
			}

			R.id.menu_student_balance -> {
				Toast.makeText(this, "Balance", Toast.LENGTH_SHORT).show()
			}

			R.id.menu_all_users -> {
				startActivity(Intent(this, AllUsersActivity::class.java))
			}

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

}