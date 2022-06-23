package com.example.teachingapp.ui.coursedetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teachingapp.R
import com.example.teachingapp.data.model.datamodel.coursemodel.TeacherCourseDetailsModel

class TeacherCourseDetailsAdapter :
	ListAdapter<TeacherCourseDetailsModel, TeacherCourseDetailsAdapter.TeacherCourseDetailsViewHolder>(
		diffUtil) {
	class TeacherCourseDetailsViewHolder(view: View):RecyclerView.ViewHolder(view) {

		val sample_tv_name:TextView = view.findViewById(R.id.sample_tv_name)
		val sample_tv_id:TextView = view.findViewById(R.id.sample_tv_id)
		val sample_tv_email:TextView = view.findViewById(R.id.sample_tv_email)

		fun bind(teacherCourseDetailsModel: TeacherCourseDetailsModel){
			sample_tv_id.text = teacherCourseDetailsModel.id
			sample_tv_name.text = teacherCourseDetailsModel.name
			sample_tv_email.text = teacherCourseDetailsModel.email
		}

	}

	companion object {
		val diffUtil = object : DiffUtil.ItemCallback<TeacherCourseDetailsModel>() {
			override fun areItemsTheSame(
				oldItem: TeacherCourseDetailsModel,
				newItem: TeacherCourseDetailsModel
			): Boolean {
				return oldItem.id == newItem.id &&
						oldItem.email == newItem.email &&
						oldItem.name == newItem.name
			}

			override fun areContentsTheSame(
				oldItem: TeacherCourseDetailsModel,
				newItem: TeacherCourseDetailsModel
			): Boolean {
				return oldItem.id == newItem.id &&
						oldItem.email == newItem.email &&
						oldItem.name == newItem.name
			}

		}
	}

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): TeacherCourseDetailsViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.enrolled_st_row,parent,false)
		return TeacherCourseDetailsViewHolder(view)
	}

	override fun onBindViewHolder(holder: TeacherCourseDetailsViewHolder, position: Int) {
		val teacherCourseDetailsModel = getItem(position)
		holder.bind(teacherCourseDetailsModel)
	}

}