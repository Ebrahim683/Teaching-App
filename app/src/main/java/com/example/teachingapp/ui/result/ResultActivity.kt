package com.example.teachingapp.ui.result

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teachingapp.Application
import com.example.teachingapp.R
import com.example.teachingapp.data.model.viewmodel.MainViewModel
import com.example.teachingapp.data.model.viewmodel.ViewModelFactory
import com.example.teachingapp.utils.OverLayLoadingManager
import com.example.teachingapp.utils.Status
import kotlinx.android.synthetic.main.activity_result.*

private const val TAG = "resultActivity"

class ResultActivity : AppCompatActivity() {

	private lateinit var email: String
	private lateinit var overLayLoadingManager: OverLayLoadingManager
	private lateinit var singleResultAdapter: SingleResultRecyclerViewAdapter

	private val studentResultViewModel by viewModels<MainViewModel> {
		ViewModelFactory((application as Application).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_result)

		overLayLoadingManager = OverLayLoadingManager(this)
		singleResultAdapter = SingleResultRecyclerViewAdapter()
		singleResultAdapter.mContext(this)
		setUpView()

		email = intent.getStringExtra("email").toString()
		if (email != null) {
			getSingleStudentResult()
		}
	}

	private fun setUpView() {
		id_result_rec.apply {
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(this@ResultActivity)
			adapter = singleResultAdapter
		}
	}

	private fun getSingleStudentResult() {
		studentResultViewModel.getSingleStudentResult(email).asLiveData().observe(this) {
			Log.d(TAG, "getSingleStudentResult: single student result response ${it.status}")

			when (it.status) {
				Status.LOADING -> {
					overLayLoadingManager.show()
				}
				Status.SUCCESS -> {
					overLayLoadingManager.dismiss()
					val results = it.data?.results
					singleResultAdapter.submitList(results)
					Log.d(TAG, "getSingleStudentResult: ${it.data}")
				}
				Status.ERROR -> {
					overLayLoadingManager.dismiss()
					Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
					Log.d(TAG, "getSingleStudentResult: error: ${it.message}")
				}
				null -> {
					overLayLoadingManager.dismiss()
					Log.d(TAG, "getSingleStudentResult: null!!!")
				}
			}
		}
	}

}