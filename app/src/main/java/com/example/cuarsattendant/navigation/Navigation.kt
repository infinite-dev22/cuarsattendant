package com.example.cuarsattendant.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cuarsattendant.firstAid.FirstAidScreen
import com.example.cuarsattendant.form.IncidentForm
import com.example.cuarsattendant.form.IncidentForm2
import com.example.cuarsattendant.incident.IncidentsScreen
import com.example.cuarsattendant.models.SharedViewModel
import com.example.cuarsattendant.viewModels.DetailViewModel
import com.example.cuarsattendant.viewModels.FirstAidViewModel
import com.example.cuarsattendant.viewModels.IncidentsViewModel

@Composable
fun NavigationMap(detailViewModel: DetailViewModel){
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()
    NavHost(navController = navController, startDestination = Screen.IncidentsScreen.route){
        composable(
            route = Screen.IncidentsScreen.route
        ){
            IncidentsScreen(navController = navController,
                viewModel = IncidentsViewModel(),
            )
        }

        composable(
            route = Screen.AidScreen.route
        ){
            FirstAidScreen(navController = navController,
                viewModel = FirstAidViewModel(),
                detailViewModel = detailViewModel,
                sharedViewModel = sharedViewModel,
            )
        }

        composable(
            route = Screen.FormScreenAdd.route,
            arguments = listOf(navArgument("incident") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) {
            IncidentForm(
                context = LocalContext.current,
                detailViewModel = detailViewModel,
                navController = navController
            )
        }

        composable(
            route = Screen.FormScreenEdit.route
        ) {
            IncidentForm2(
                LocalContext.current,
                detailViewModel,
                sharedViewModel = sharedViewModel,
                navController
            )
        }
    }
}
