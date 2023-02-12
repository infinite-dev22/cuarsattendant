package com.example.cuarsattendant.models

data class IncidentInformation(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val address: String = "",
    val phone: String = "",
    val incidentLocation: String = "",
    val incidentDescription: String = "",
    val incidentWitnessAvailable: Boolean = false,
    val incidentVictimInjured: Boolean = false,
    val incidentInjuryDescription: String = ""
)