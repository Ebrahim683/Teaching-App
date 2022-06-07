package com.example.teachingapp.ui.auth

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.teachingapp.R
import com.example.teachingapp.ui.dashboard.teacherdashboard.TeacherDashboardActivity
import com.example.teachingapp.data.model.datamodel.teachermodel.TeacherRegisterModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_student_register.id_address
import kotlinx.android.synthetic.main.activity_student_register.id_balance
import kotlinx.android.synthetic.main.activity_student_register.id_dept
import kotlinx.android.synthetic.main.activity_student_register.id_level
import kotlinx.android.synthetic.main.activity_student_register.id_mobile
import kotlinx.android.synthetic.main.activity_student_register.id_user_id
import kotlinx.android.synthetic.main.activity_teacher_register.*
import kotlinx.android.synthetic.main.activity_teacher_register.id_confirm_password
import kotlinx.android.synthetic.main.activity_teacher_register.id_email
import kotlinx.android.synthetic.main.activity_teacher_register.id_name
import kotlinx.android.synthetic.main.activity_teacher_register.id_password
import java.util.*


private const val TAG = "teacherRegisterActivity"
private const val role = "Teacher"

class TeacherRegisterActivity : AppCompatActivity() {

    private lateinit var tAuth: FirebaseAuth
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var level: String
    private lateinit var id: String
    private lateinit var dept: String
    private lateinit var mobile: String
    private lateinit var address: String

    //    private lateinit var course: ArrayList<String>
    private lateinit var balance: String
    private lateinit var registerDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_register)

        val calendar = Calendar.getInstance(Locale.getDefault())
        val date = calendar.get(Calendar.DATE)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        registerDate = "$date/$month/$year"
        Log.d(TAG, "onCreate: $registerDate")



        id_tv_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        id_btn_teacher_register.setOnClickListener {
            createTeacherAccount()
        }

    }

    private fun createTeacherAccount() {
        tAuth = FirebaseAuth.getInstance()
        name = id_name.editText!!.text.toString()
        email = id_email.editText!!.text.toString().trim()
        password = id_password.editText!!.text.toString().trim()
        level = id_level.editText!!.text.toString()
        id = id_user_id.editText!!.text.toString().trim()
        dept = id_dept.editText!!.text.toString()
        mobile = id_mobile.editText!!.text.toString().trim()
        address = id_address.editText!!.text.toString()
        balance = id_balance.editText!!.text.toString().trim()
        val courses = listOf<String>(id_courses.editText!!.text.toString().trim())
        Log.d(TAG, "onCreate: $courses")

        if (name.isEmpty()) {
            id_name.editText!!.error = "Enter name"
        } else if (email.isEmpty()) {
            id_email.editText!!.error = "Enter email"
        } else if (id.isEmpty()) {
            id_user_id.editText!!.error = "Enter your ID"
        } else if (dept.isEmpty()) {
            id_dept.editText!!.error = "Enter your department"
        } else if (level.isEmpty()) {
            id_level.editText!!.error = "Enter your semester level"
        } else if (mobile.isEmpty()) {
            id_mobile.editText!!.error = "Enter your mobile number"
        } else if (id_courses.editText!!.text.isEmpty()) {
            id_courses.editText!!.error = "Enter parents contact number"
        } else if (address.isEmpty()) {
            id_address.editText!!.error = "Enter your address"
        } else if (balance.isEmpty()) {
            id_balance.editText!!.error = "Enter balance"
        } else if (password.isEmpty()) {
            id_password.editText!!.error = "Enter password"
        } else if (id_confirm_password.editText!!.text.toString().isEmpty()) {
            id_confirm_password.editText!!.error = "Confirm your password"
        } else if (id_confirm_password.editText!!.text.toString() != password) {
            id_confirm_password.editText!!.error = "Password doesn't match"
        } else {

            val alertDialog = AlertDialog.Builder(this)
                .setMessage("Creating account...")
                .setCancelable(false)
                .show()

            tAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val data = task.result.user
                    val teacherRegisterModel = TeacherRegisterModel(
                        address,
                        balance,
                        courses,
                        dept,
                        email,
                        id,
                        level,
                        mobile,
                        name,
                        password,
                        registerDate,
                        role
                    )
                    Log.d(TAG, "createTeacherAccount: ${data!!.email}")
                    alertDialog.dismiss()
                    startActivity(Intent(this, TeacherDashboardActivity::class.java))
                    finishAffinity()
                } else {
                    alertDialog.dismiss()
                    Toast.makeText(this, "${task.exception?.localizedMessage}", Toast.LENGTH_SHORT)
                        .show()
                    Log.d(TAG, "createStudentAccount: ${task.exception?.localizedMessage}")
                }
            }
        }

    }

    private fun saveDataInDatabase(teacherRegisterModel: TeacherRegisterModel) {
        Toast.makeText(
            this,
            "${teacherRegisterModel.name}\n${teacherRegisterModel.email}\n${teacherRegisterModel.registerDate}",
            Toast.LENGTH_SHORT
        ).show()
        Log.d(
            TAG, "saveDataInDatabase: ${teacherRegisterModel.name}\n" +
                    "${teacherRegisterModel.email}\n" +
                    "${teacherRegisterModel.registerDate}"
        )
    }

}