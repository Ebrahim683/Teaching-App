package com.example.teachingapp.ui.dashboard.teacherdashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.layout.Layout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teachingapp.R
import com.example.teachingapp.data.model.datamodel.teachermodel.TeacherCourseModel
import com.squareup.picasso.Picasso

class TeacherDashboardAdapter:
	ListAdapter<TeacherCourseModel, TeacherDashboardAdapter.TeacherDashboardViewHolder>(
		DIFF_UTIL_CALL_BACK) {
	class TeacherDashboardViewHolder(view:View):RecyclerView.ViewHolder(view) {

		val sample_teacher_course_image:ImageView = view.findViewById(R.id.sample_teacher_course_image)
		val sample_teacher_course_title:TextView = view.findViewById(R.id.sample_teacher_course_title)
		val sample_teacher_course_id:TextView = view.findViewById(R.id.sample_teacher_course_id)
		val sample_enrolled_st_list_for_teacher:TextView = view.findViewById(R.id.sample_enrolled_st_list_for_teacher)
		val btn_sample_teacher_dashboard:Button = view.findViewById(R.id.btn_sample_teacher_dashboard)

		fun bind(teacherCourseModel: TeacherCourseModel){
			sample_teacher_course_id.text = teacherCourseModel.courseId
			sample_teacher_course_title.text = teacherCourseModel.courseTitle
			sample_enrolled_st_list_for_teacher.text = teacherCourseModel.studentList.toString()
			Picasso.get()
				.load(teacherCourseModel.courseImg)
				.placeholder(R.drawable.loading)
				.into(sample_teacher_course_image)
		}

	}

	companion object{
		val DIFF_UTIL_CALL_BACK = object : DiffUtil.ItemCallback<TeacherCourseModel>() {
			override fun areItemsTheSame(
				oldItem: TeacherCourseModel,
				newItem: TeacherCourseModel
			): Boolean {
				return oldItem._id == newItem._id &&
						oldItem.courseId == newItem.courseId &&
						oldItem.courseTitle == newItem.courseTitle &&
						oldItem.courseImg == newItem.courseImg &&
						oldItem.studentList == newItem.studentList
			}

			override fun areContentsTheSame(
				oldItem: TeacherCourseModel,
				newItem: TeacherCourseModel
			): Boolean {
				return oldItem._id == newItem._id &&
						oldItem.courseId == newItem.courseId &&
						oldItem.courseTitle == newItem.courseTitle &&
						oldItem.courseImg == newItem.courseImg &&
						oldItem.studentList == newItem.studentList
			}

		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherDashboardViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.teacher_rec_row,parent,false)
		return TeacherDashboardViewHolder(view)
	}

	override fun onBindViewHolder(holder: TeacherDashboardViewHolder, position: Int) {
		val teacherCourseModel = getItem(position)
		holder.bind(teacherCourseModel)
	}
}