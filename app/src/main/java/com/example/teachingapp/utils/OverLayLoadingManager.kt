package com.example.teachingapp.utils

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.example.teachingapp.R

private const val TAG = "overLayLoadingManager"

class OverLayLoadingManager(context: Context) {

	private var builder: AlertDialog.Builder? = null
	private var dialog: AlertDialog? = null

	init {

		Log.d(TAG, "init()")
		builder = AlertDialog.Builder(context, R.style.Custom_ad)
		val layoutInflater =
			context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val view: View = layoutInflater.inflate(R.layout.overloading_layout, null)

		builder!!.setView(view)
		dialog = builder!!.create()
		dialog!!.setCancelable(false)
	}

	fun show() {
		if (dialog?.isShowing == false) {
			dialog?.show()
		}
	}

	fun dismiss() {
		if (dialog?.isShowing == true) {
			dialog?.dismiss()
		}
	}

}