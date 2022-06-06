package com.example.teachingapp.ui.dashboard.studentdashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teachingapp.R
import com.example.teachingapp.ui.data.model.StudentDashBoardModel
import com.example.teachingapp.ui.utils.StudentDashboardItemClickListener

class StudentDashboardAdapter :
    ListAdapter<StudentDashBoardModel, StudentDashboardAdapter.StudentDashboardViewHolder>(
        DIFF_UTILS_CALL_BACK
    ) {

    private var studentDashboardItemClickListener: StudentDashboardItemClickListener? = null

    fun studentItemClick(studentDashboardItemClickListener: StudentDashboardItemClickListener) {
        this.studentDashboardItemClickListener = studentDashboardItemClickListener
    }

    class StudentDashboardViewHolder(
        view: View,
        var studentDashboardItemClickListener: StudentDashboardItemClickListener?
    ) : RecyclerView.ViewHolder(view) {
        val sample_st_rec_img: ImageView = view.findViewById(R.id.sample_st_rec_img)
        val sample_st_rec_title: TextView = view.findViewById(R.id.sample_st_rec_title)

        fun bind(studentDashBoardModel: StudentDashBoardModel) {
            sample_st_rec_img.setImageResource(studentDashBoardModel.image!!.toInt())
            sample_st_rec_title.text = studentDashBoardModel.title

            itemView.setOnClickListener {
                if (RecyclerView.NO_POSITION != adapterPosition) {
                    studentDashboardItemClickListener!!.onStudentItemClick(
                        itemView,
                        adapterPosition,
                        studentDashBoardModel
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentDashboardViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.student_rec_row, parent, false)
        return StudentDashboardViewHolder(view, studentDashboardItemClickListener!!)
    }

    override fun onBindViewHolder(holder: StudentDashboardViewHolder, position: Int) {
        val studentDashBoardModel = getItem(position)
        holder.bind(studentDashBoardModel)
    }

    companion object {
        val DIFF_UTILS_CALL_BACK = object : DiffUtil.ItemCallback<StudentDashBoardModel>() {
            override fun areItemsTheSame(
                oldItem: StudentDashBoardModel,
                newItem: StudentDashBoardModel
            ): Boolean {
                return oldItem.image == newItem.image && oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: StudentDashBoardModel,
                newItem: StudentDashBoardModel
            ): Boolean {
                return oldItem.image == newItem.image && oldItem.title == newItem.title
            }

        }
    }

}
