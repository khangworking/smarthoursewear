package com.example.mysmarthouse.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class DevicesScreenViewModel: ViewModel() {
    private val _loading = MutableStateFlow(true)
    val loading = _loading.value
}