package com.example.teachingapp.ui.auth

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.teachingapp.R
import com.example.teachingapp.ui.dashboard.studentdashboard.StudentDashboardActivity
import com.example.teachingapp.utils.SharedPrifUtils
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

private const val TAG = "loginActivity"

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var sharedPrifUtils: SharedPrifUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPrifUtils = SharedPrifUtils(this)
        mAuth = FirebaseAuth.getInstance()

        id_tv_signup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        id_btn_login.setOnClickListener {
            signInUser()
        }

        id_forgot_pass.setOnClickListener {
            forgotPassword()
        }

        if (sharedPrifUtils.isLoggedIn()) {
            startActivity(Intent(this, StudentDashboardActivity::class.java))
            finish()
        }

    }

    private fun signInUser() {
        email = id_email.editText!!.text.toString().trim()
        password = id_password.editText!!.text.toString().trim()

        if (email.isEmpty()) {
            id_email.editText!!.error = "Enter valid email"
        } else if (password.isEmpty()) {
            id_password.editText!!.error = "Enter password"
        } else {

            val alertDialog = AlertDialog.Builder(this)
                .setMessage("Logging in...")
                .setCancelable(false)
                .show()

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val data = task.result.user
                    alertDialog.dismiss()
                    sharedPrifUtils.saveUser(data!!.email.toString())
                    Log.d(TAG, "signInUser: ${data.email}")
                    startActivity(Intent(this, StudentDashboardActivity::class.java))
                    finish()
                } else {
                    alertDialog.dismiss()
                    Toast.makeText(this, "${task.exception?.localizedMessage}", Toast.LENGTH_SHORT)
                        .show()
                    Log.d(TAG, "createStudentAccount: ${task.exception?.localizedMessage}")
                }
            }
        }
    }

    private fun forgotPassword() {
        email = id_email.editText!!.text.toString().trim()
        if (email.isEmpty()) {
            id_email.editText!!.error = "Enter valid email"
        } else {
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Reset link sent to your mail", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "forgotPasswordSuccess: ${task.result}")
                } else {
                    Toast.makeText(this, task.exception!!.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                    Log.d(TAG, "forgotPasswordFailed: ${task.exception!!.localizedMessage}")
                }
            }
        }

    }

}