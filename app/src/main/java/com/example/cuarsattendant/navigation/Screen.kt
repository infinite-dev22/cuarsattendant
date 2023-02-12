package com.example.cuarsattendant.navigation

sealed class Screen (val route: String){
    object IncidentsScreen: Screen("mainScreen")
    object AidScreen: Screen("aidScreen")
    object FormScreenAdd: Screen("formScreen")
    object FormScreenEdit: Screen("formScreenEdit")
}