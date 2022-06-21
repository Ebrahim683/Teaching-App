package com.example.teachingapp.ui.balance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teachingapp.R
import com.example.teachingapp.data.model.datamodel.balance.BalanceAdapter
import com.example.teachingapp.data.model.datamodel.balance.BalanceModel
import com.example.teachingapp.utils.OverLayLoadingManager
import kotlinx.android.synthetic.main.activity_balance.*

class BalanceActivity : AppCompatActivity() {

	private lateinit var overLayLoadingManager: OverLayLoadingManager
	private lateinit var balanceAdapter: BalanceAdapter
	private lateinit var arrayList: ArrayList<BalanceModel>

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_balance)


		overLayLoadingManager = OverLayLoadingManager(this)
		balanceAdapter = BalanceAdapter()
		arrayList = ArrayList()

		arrayList.add(BalanceModel("Tuition Fee","1200"))
		arrayList.add(BalanceModel("Tuition Fee","1200"))
		arrayList.add(BalanceModel("Tuition Fee","1200"))
		arrayList.add(BalanceModel("Tuition Fee","1200"))
		arrayList.add(BalanceModel("Tuition Fee","1200"))

		balanceAdapter.submitList(arrayList)

		setUpView()
	}

	private fun setUpView() {
		id_balance_rec.apply {
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(this@BalanceActivity)
			adapter = balanceAdapter
		}
	}

}