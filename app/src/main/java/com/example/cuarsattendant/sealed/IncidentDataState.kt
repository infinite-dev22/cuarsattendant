package com.example.cuarsattendant.sealed

import com.example.cuarsattendant.models.IncidentInformation

sealed class IncidentDataState {
    class Success(val data: MutableList<IncidentInformation>) : IncidentDataState()
    class Failure(val message: String) : IncidentDataState()
    object Loading : IncidentDataState()
    object Empty : IncidentDataState()
}