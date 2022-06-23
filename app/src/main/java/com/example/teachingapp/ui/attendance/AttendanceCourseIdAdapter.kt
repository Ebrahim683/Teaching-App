package com.example.teachingapp.ui.attendance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teachingapp.R
import com.example.teachingapp.data.model.datamodel.attendance.AttendanceCourseModel
import com.example.teachingapp.utils.AttendanceClickListener

class AttendanceCourseIdAdapter :
	ListAdapter<AttendanceCourseModel, AttendanceCourseIdAdapter.AttendanceCourseIdViewHolder>(
		diffUtil
	) {

	private var attendanceClickListener: AttendanceClickListener? = null
	fun onDateClick(attendanceClickListener: AttendanceClickListener) {
		this.attendanceClickListener = attendanceClickListener
	}

	class AttendanceCourseIdViewHolder(
		view: View,
		val attendanceClickListener: AttendanceClickListener
	) : RecyclerView.ViewHolder(view) {
		val btn_sample_course_id_att: Button = view.findViewById(R.id.btn_sample_course_id_att)
		val sample_att_st_list: TextView = view.findViewById(R.id.sample_att_st_list)

		fun bind(attendanceCourseModel: AttendanceCourseModel) {
			btn_sample_course_id_att.setText("Date: ${attendanceCourseModel.date}")
			sample_att_st_list.text = attendanceCourseModel.stuList.toString()
			btn_sample_course_id_att.setOnClickListener {
				if (RecyclerView.NO_POSITION != adapterPosition) {
					attendanceClickListener.onDateClick(
						itemView,
						adapterPosition,
						attendanceCourseModel
					)
				}
			}
		}
	}

	companion object {
		val diffUtil = object : DiffUtil.ItemCallback<AttendanceCourseModel>() {
			override fun areItemsTheSame(
				oldItem: AttendanceCourseModel,
				newItem: AttendanceCourseModel
			): Boolean {
				return oldItem._id == newItem._id &&
						oldItem.courseId == newItem.courseId &&
						oldItem.date == newItem.date &&
						oldItem.stuList == newItem.stuList
			}

			override fun areContentsTheSame(
				oldItem: AttendanceCourseModel,
				newItem: AttendanceCourseModel
			): Boolean {
				return oldItem._id == newItem._id &&
						oldItem.courseId == newItem.courseId &&
						oldItem.date == newItem.date &&
						oldItem.stuList == newItem.stuList
			}

		}
	}

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): AttendanceCourseIdViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.att_courseid_rec_row, parent, false)
		return AttendanceCourseIdViewHolder(view, attendanceClickListener!!)
	}

	override fun onBindViewHolder(holder: AttendanceCourseIdViewHolder, position: Int) {
		val attendanceCourseModel = getItem(position)
		holder.bind(attendanceCourseModel)
	}

}