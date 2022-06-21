package com.example.teachingapp.ui.dashboard.studentdashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teachingapp.R
import com.example.teachingapp.data.model.datamodel.coursemodel.CoursesModel
import com.example.teachingapp.utils.CourseItemCLickListener
import com.squareup.picasso.Picasso

class StudentDashboardAdapter() :
	ListAdapter<CoursesModel, StudentDashboardAdapter.StudentDashboardViewHolder>(
		DIFF_UTILS_CALL_BACK
	) {

	private var context: Context? = null
	private var courseItemCLickListener: CourseItemCLickListener? = null

	fun setContext(context: Context) {
		this.context = context
	}

	fun studentItemClick(courseItemCLickListener: CourseItemCLickListener) {
		this.courseItemCLickListener = courseItemCLickListener
	}

	class StudentDashboardViewHolder(
		view: View,
		var courseItemCLickListener: CourseItemCLickListener?,
		var context: Context
	) : RecyclerView.ViewHolder(view) {
		val sample_st_rec_img: ImageView = view.findViewById(R.id.sample_st_rec_img)
		val sample_st_rec_title: TextView = view.findViewById(R.id.sample_st_rec_title)

		fun bind(coursesModel: CoursesModel) {

			Picasso.get()
				.load(coursesModel.courseImg)
				.placeholder(R.drawable.loading)
				.into(sample_st_rec_img);

			sample_st_rec_title.text = coursesModel.courseTitle

			itemView.setOnClickListener {
				if (RecyclerView.NO_POSITION != adapterPosition) {
					courseItemCLickListener!!.onStudentItemClick(
						itemView,
						adapterPosition,
						coursesModel
					)
				}
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentDashboardViewHolder {
		val view =
			LayoutInflater.from(parent.context).inflate(R.layout.student_rec_row, parent, false)
		return StudentDashboardViewHolder(view, courseItemCLickListener!!, context!!)
	}

	override fun onBindViewHolder(holder: StudentDashboardViewHolder, position: Int) {
		val coursesModel = getItem(position)
		holder.bind(coursesModel)
	}

	companion object {
		val DIFF_UTILS_CALL_BACK = object : DiffUtil.ItemCallback<CoursesModel>() {
			override fun areItemsTheSame(oldItem: CoursesModel, newItem: CoursesModel): Boolean {
				return oldItem._id == newItem._id &&
						oldItem.courseImg == newItem.courseImg &&
						oldItem.courseTitle == newItem.courseTitle &&
						oldItem.courseId == newItem.courseId
			}

			override fun areContentsTheSame(oldItem: CoursesModel, newItem: CoursesModel): Boolean {
				return oldItem._id == newItem._id &&
						oldItem.courseImg == newItem.courseImg &&
						oldItem.courseTitle == newItem.courseTitle &&
						oldItem.courseId == newItem.courseId
			}


		}
	}

}
