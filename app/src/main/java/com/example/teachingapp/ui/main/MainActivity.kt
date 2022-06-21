package com.example.teachingapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.teachingapp.R

private const val TAG = "mainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val title = intent.getStringExtra("title")
        Log.d(TAG, "onCreate: $title")

    }
}