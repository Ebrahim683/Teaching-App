package com.example.teachingapp.ui.result

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teachingapp.R
import com.example.teachingapp.data.model.datamodel.resultmodel.Result

class SingleResultRecyclerViewAdapter() :
	ListAdapter<Result, SingleResultRecyclerViewAdapter.SingleResultViewHolder>(
		DIFF_UTIL_CALL_BACK
	) {

	var context: Context? = null
	fun mContext(context: Context) {
		this.context = context
	}

	class SingleResultViewHolder(view: View, var context: Context) : RecyclerView.ViewHolder(view) {
		//		val id_single_result_spinner: Spinner = view.findViewById(R.id.id_single_result_spinner)
		val id_layout_single_result_sample: LinearLayout =
			view.findViewById(R.id.id_layout_single_result_sample)
		val id_sample_course_code_layout: RelativeLayout =
			view.findViewById(R.id.id_sample_course_code_layout)
		val id_sample_course_code: TextView = view.findViewById(R.id.id_sample_course_code)
		val id_sample_q1: TextView = view.findViewById(R.id.id_sample_q1)
		val id_sample_q2: TextView = view.findViewById(R.id.id_sample_q2)
		val id_sample_q3: TextView = view.findViewById(R.id.id_sample_q3)
		val id_sample_assignment: TextView = view.findViewById(R.id.id_sample_assignment)
		val id_sample_presentation: TextView = view.findViewById(R.id.id_sample_presentation)
		val id_sample_mid: TextView = view.findViewById(R.id.id_sample_mid)
		val id_sample_final: TextView = view.findViewById(R.id.id_sample_final)
		val id_sample_grade: TextView = view.findViewById(R.id.id_sample_grade)
		val id_sample_drop_down: ImageView = view.findViewById(R.id.id_sample_drop_down)
		fun bind(result: Result) {

			val assignment =
				(result.assignment1?.toDouble()!! + result.assignment2?.toDouble()!! + result.assignment3?.toDouble()!! + result.quizAdditional?.toDouble()!!) / 4

			id_sample_q1.text = " ${result.quiz1.toString()}"
			id_sample_q2.text = " ${result.quiz2.toString()}"
			id_sample_q3.text = " ${result.quiz3.toString()}"
			id_sample_assignment.text = " ${assignment.toString()}"
			id_sample_presentation.text = " ${result.presentation.toString()}"
			id_sample_mid.text = " ${result.mid.toString()}"
			id_sample_final.text = " ${result.final.toString()}"
			id_sample_grade.text = "A+"
			id_sample_course_code.text = " ${result.courseId.toString()}"

			id_sample_course_code_layout.setOnClickListener {
				if (id_layout_single_result_sample.isVisible) {
					id_layout_single_result_sample.visibility = View.GONE
					id_sample_drop_down.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)

				} else {
					id_layout_single_result_sample.visibility = View.VISIBLE
					id_sample_drop_down.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)
				}
			}

//			val courseList = listOf(result.courseId)
//			val arrayAdapter = ArrayAdapter<String>(
//				context,
//				android.R.layout.simple_spinner_dropdown_item,
//				courseList
//			)
//			id_single_result_spinner.adapter = arrayAdapter

		}

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleResultViewHolder {
		val view =
			LayoutInflater.from(parent.context).inflate(R.layout.single_result_row, parent, false)
		return SingleResultViewHolder(view, context!!)
	}

	override fun onBindViewHolder(holder: SingleResultViewHolder, position: Int) {
		val result = getItem(position)
		holder.bind(result)
	}

	companion object {
		val DIFF_UTIL_CALL_BACK = object : DiffUtil.ItemCallback<Result>() {
			override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
				return oldItem.assignment1 == newItem.assignment1 &&
						oldItem.assignment2 == newItem.assignment2 &&
						oldItem.assignment3 == newItem.assignment3 &&
						oldItem.quiz1 == newItem.quiz2 &&
						oldItem.quiz2 == newItem.quiz2 &&
						oldItem.quiz3 == newItem.quiz3 &&
						oldItem.mid == newItem.mid &&
						oldItem.courseId == newItem.courseId &&
						oldItem.final == newItem.final &&
						oldItem.presentation == newItem.presentation &&
						oldItem.quizAdditional == newItem.quizAdditional &&
						oldItem.semesterName == newItem.semesterName
			}

			override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
				return oldItem.assignment1 == newItem.assignment1 &&
						oldItem.assignment2 == newItem.assignment2 &&
						oldItem.assignment3 == newItem.assignment3 &&
						oldItem.quiz1 == newItem.quiz2 &&
						oldItem.quiz2 == newItem.quiz2 &&
						oldItem.quiz3 == newItem.quiz3 &&
						oldItem.mid == newItem.mid &&
						oldItem.courseId == newItem.courseId &&
						oldItem.final == newItem.final &&
						oldItem.presentation == newItem.presentation &&
						oldItem.quizAdditional == newItem.quizAdditional &&
						oldItem.semesterName == newItem.semesterName
			}

		}
	}

}