package com.example.teachingapp.data.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.teachingapp.data.local.repository.Repository

class ViewModelFactory(private var repository: Repository):ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return MainViewModel(repository) as T
	}

}