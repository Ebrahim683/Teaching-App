package com.example.teachingapp.data.model.datamodel.response

data class UpdateProfileResponse(
	val acknowledged: Boolean?, // true
	val modifiedCount: Int?, // 1
	val upsertedId: Any?, // null
	val upsertedCount: Int?, // 0
	val matchedCount: Int? // 1
)