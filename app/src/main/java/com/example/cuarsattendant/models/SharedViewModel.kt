package com.example.cuarsattendant.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var firstAid by mutableStateOf(FirstAid())
        private set

    fun addFirstAid(firstAid: FirstAid) {
        this.firstAid = firstAid
    }
}