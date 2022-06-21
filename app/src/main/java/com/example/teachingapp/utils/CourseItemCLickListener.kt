package com.example.teachingapp.utils

import android.view.View
import com.example.teachingapp.data.model.datamodel.coursemodel.CoursesModel
import com.example.teachingapp.data.model.datamodel.teachermodel.TeacherCourseModel

interface CourseItemCLickListener {
	fun onStudentItemClick(view: View, position: Int, coursesModel: CoursesModel)
	fun onTeacherItemClick(view: View, position: Int, teacherCourseModel: TeacherCourseModel)
}