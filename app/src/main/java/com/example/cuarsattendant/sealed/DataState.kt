package com.example.cuarsattendant.sealed

import com.example.cuarsattendant.models.FirstAid

sealed class DataState {
    class Success(val data: MutableList<FirstAid>) : DataState()
    class Failure(val message: String) : DataState()
    object Loading : DataState()
    object Empty : DataState()
}