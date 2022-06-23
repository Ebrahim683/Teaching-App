package com.example.teachingapp.ui.attendance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teachingapp.R
import com.example.teachingapp.data.model.datamodel.attendance.SingleStudentAttModel

private const val TAG = "attendanceStudentListAdapter"

class AttendanceStudentListAdapter :
	ListAdapter<SingleStudentAttModel, AttendanceStudentListAdapter.AttendanceStudentListHolder>(
		diffUtil
	) {
	class AttendanceStudentListHolder(view: View) : RecyclerView.ViewHolder(view) {
		val st_att_txt: TextView = view.findViewById(R.id.st_att_txt)

		fun bind(singleStudentAttModel: SingleStudentAttModel) {
			st_att_txt.text = singleStudentAttModel.stId
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceStudentListHolder {
		val view =
			LayoutInflater.from(parent.context).inflate(R.layout.st_att_rec_row, parent, false)
		return AttendanceStudentListHolder(view)
	}

	override fun onBindViewHolder(holder: AttendanceStudentListHolder, position: Int) {
		val singleStudentAttModel = getItem(position)
		holder.bind(singleStudentAttModel)
	}

	companion object {
		val diffUtil = object : DiffUtil.ItemCallback<SingleStudentAttModel>() {
			override fun areItemsTheSame(
				oldItem: SingleStudentAttModel,
				newItem: SingleStudentAttModel
			): Boolean {
				return oldItem.stId == newItem.stId
			}

			override fun areContentsTheSame(
				oldItem: SingleStudentAttModel,
				newItem: SingleStudentAttModel
			): Boolean {
				return oldItem.stId == newItem.stId
			}

		}
	}

}