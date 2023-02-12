package com.example.cuarsattendant.form

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cuarsattendant.partials.EditTextInput
import com.example.cuarsattendant.R
import com.example.cuarsattendant.partials.TopAppBarSlave
import com.example.cuarsattendant.models.SharedViewModel
import com.example.cuarsattendant.viewModels.DetailUiState
import com.example.cuarsattendant.viewModels.DetailViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentForm2(
    context: Context,
    detailViewModel: DetailViewModel?,
    sharedViewModel: SharedViewModel,
    navController: NavController
) {
    detailViewModel?.setEditFields(sharedViewModel.firstAid)

    val detailUiState = detailViewModel?.detailUiState ?: DetailUiState()

    val isFormsNotBlank = detailUiState.name.isNotBlank()
            && detailUiState.description.isNotBlank()

    MaterialTheme {
        Scaffold(
            topBar = { TopAppBar(title = { TopAppBarSlave(topBarTitle = R.string.form_topbar_title, navController) }) },
        ) {
            Column(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .fillMaxSize()
                    .padding(paddingValues = it)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                EditTextInput(
                    R.string.victim_name,
                    value = detailUiState.name,
                    onValueChange = { detailViewModel?.onNameChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(8.dp))
                EditTextInput(
                    R.string.description,
                    value = detailUiState.description,
                    onValueChange = { detailViewModel?.onDescriptionChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .height(400.dp)
                        .padding(8.dp))
                Button(
                    onClick = {
                        if (isFormsNotBlank) {
                            detailViewModel?.addGenFirstAid()
                            Toast.makeText(context, "First aid Saved Successfully", Toast.LENGTH_LONG).show()
                            detailViewModel?.resetState()
                            navController.popBackStack()
                        } else {
                            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_LONG).show()
                        }
                    },
                    Modifier.padding(0.dp, 20.dp, 0.dp, 50.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(text = "Save")
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(20.dp),
                        color = Color.Transparent
                    )
                    Image(
                        painterResource(id = R.drawable.save),
                        contentDescription ="Cart button icon",
                        modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}