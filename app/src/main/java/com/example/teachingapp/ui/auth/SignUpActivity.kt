package com.example.teachingapp.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teachingapp.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        id_btn_teacher_signup.setOnClickListener {
            startActivity(Intent(this,TeacherRegisterActivity::class.java))
        }

        id_btn_student_signup.setOnClickListener {
            startActivity(Intent(this,StudentRegisterActivity::class.java))
        }

    }
}