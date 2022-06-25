package com.example.teachingapp.ui.balance

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teachingapp.Application
import com.example.teachingapp.R
import com.example.teachingapp.data.model.datamodel.balance.BalanceAdapter
import com.example.teachingapp.data.model.datamodel.balance.BalanceModel
import com.example.teachingapp.data.model.viewmodel.MainViewModel
import com.example.teachingapp.data.model.viewmodel.ViewModelFactory
import com.example.teachingapp.utils.OverLayLoadingManager
import com.example.teachingapp.utils.Status
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_balance.*

private const val TAG = "balanceActivity"

class BalanceActivity : AppCompatActivity() {

	private lateinit var overLayLoadingManager: OverLayLoadingManager
	private lateinit var balanceAdapter: BalanceAdapter
	private lateinit var arrayList: ArrayList<BalanceModel>
	private lateinit var email: String
	private lateinit var auth: FirebaseAuth
	private lateinit var iRole: String

	private val balanceViewModel by viewModels<MainViewModel> {
		ViewModelFactory((application as Application).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_balance)

		iRole = intent.getStringExtra("role").toString()
		if (iRole == "student") {
			window.statusBarColor = resources.getColor(R.color.student_color)
			supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.student_color)))
			text_layout.setBackgroundColor(resources.getColor(R.color.student_color))
		} else if (iRole == "teacher") {
			window.statusBarColor = resources.getColor(R.color.blue)
			supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.blue)))
			text_layout.setBackgroundColor(resources.getColor(R.color.blue))
		}

		overLayLoadingManager = OverLayLoadingManager(this)
		balanceAdapter = BalanceAdapter()
		arrayList = ArrayList()

		auth = FirebaseAuth.getInstance()
		email = auth.currentUser?.email.toString()

		arrayList.add(BalanceModel("Tuition Fee", "1200"))

		balanceAdapter.submitList(arrayList)

		getUserProfile(email)
		setUpView()
	}

	private fun setUpView() {
		id_balance_rec.apply {
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(this@BalanceActivity)
			adapter = balanceAdapter
		}
	}

	private fun getUserProfile(email: String) {
		balanceViewModel.getSingleUser(email).asLiveData()
			.observe(this) {
				Log.d(TAG, "getUserProfile: get user profile balance response: ${it.status}")
				when (it.status) {
					Status.LOADING -> {
						overLayLoadingManager.show()
					}
					Status.SUCCESS -> {
						overLayLoadingManager.dismiss()
						val value = it.data
						id_balance_hi_text.text = "Hi! ${it.data?.name}"
						Log.d(TAG, "getUserProfile: success: $value")

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

}