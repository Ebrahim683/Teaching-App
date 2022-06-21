package com.example.teachingapp.data.model.datamodel.balance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teachingapp.R

class BalanceAdapter :
	ListAdapter<BalanceModel, BalanceAdapter.BalanceViewHolder>(DIFF_UTIL_CALL_BACK) {

	class BalanceViewHolder(view: View) : RecyclerView.ViewHolder(view) {

		val id_sample_balance_number: TextView = view.findViewById(R.id.id_sample_balance_number)
		val id_sample_purpose: TextView = view.findViewById(R.id.id_sample_purpose)
		val id_sample_amount: TextView = view.findViewById(R.id.id_sample_amount)

		fun bind(balanceModel: BalanceModel) {
			val position = adapterPosition
			id_sample_balance_number.text = (position + 1).toString()
			id_sample_purpose.text = balanceModel.purpose
			id_sample_amount.text = balanceModel.amount
		}

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder {
		val view =
			LayoutInflater.from(parent.context).inflate(R.layout.balance_rec_row, parent, false)
		return BalanceViewHolder(view)
	}

	override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
		val balanceModel = getItem(position)
		holder.bind(balanceModel)
	}

	companion object {
		val DIFF_UTIL_CALL_BACK = object : DiffUtil.ItemCallback<BalanceModel>() {
			override fun areItemsTheSame(oldItem: BalanceModel, newItem: BalanceModel): Boolean {
				return oldItem.purpose == newItem.purpose &&
						oldItem.amount == newItem.amount
			}

			override fun areContentsTheSame(oldItem: BalanceModel, newItem: BalanceModel): Boolean {
				return oldItem.purpose == newItem.purpose &&
						oldItem.amount == newItem.amount
			}

		}
	}
}