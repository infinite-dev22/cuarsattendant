package com.example.cuarsattendant.incident

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cuarsattendant.R
import com.example.cuarsattendant.models.IncidentInformation
import com.example.cuarsattendant.navigation.Screen
import com.example.cuarsattendant.partials.FloatingButton
import com.example.cuarsattendant.partials.TopAppBarMain
import com.example.cuarsattendant.sealed.IncidentDataState
import com.example.cuarsattendant.viewModels.IncidentsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentsScreen(
    navController: NavController,
    viewModel: IncidentsViewModel,
) {
    Scaffold(
        topBar = { TopAppBar(title = { TopAppBarMain(topBarTitle = R.string.main_topbar_title) }) },

        floatingActionButton = {
            FloatingButton("First Aid", Icons.Rounded.List, "First Aid") {
                navController.navigate(
                    Screen.AidScreen.route
                )
            }
        },
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues = it),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val result2 = viewModel.response.value) {
                is IncidentDataState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is IncidentDataState.Success -> {
                    ShowLazyList(
                        result2.data,
                    )
                }

                is IncidentDataState.Failure -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = result2.message,
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        )
                    }
                }

                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error Fetching data",
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShowLazyList(
    incidents: MutableList<IncidentInformation>,
) {
    if (incidents.size > 0) {
        LazyColumn {
            items(incidents) { incident ->
                CardItem(
                    incident,
                )
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Nothing to show here",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun CardItem(
    incident: IncidentInformation,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Row {
                Column {
                    Text(
                        text = "Name: ${incident.name}",
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .width(1.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Telephone: ${incident.phone}",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    Text(
                        text = "Email: ${incident.email}",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    Text(
                        text = "Address: ${incident.address}",
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    Text(
                        text = "Accident at: ${incident.incidentLocation}",
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
        }

    }
}