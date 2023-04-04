package com.example.cuarsattendant.partials

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cuarsattendant.user
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess

@Composable
fun FloatingButton(
    text: String,
    icon: ImageVector,
    contentDesc: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        ExtendedFloatingActionButton(
            text = {
                Text(text = text, color = Color.White)
            },
            icon = {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDesc,
                    tint = Color.White,
                )
            },
            onClick = onClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextInput(
    label: Int,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    Row(horizontalArrangement = Arrangement.Center) {
        OutlinedTextField(
            label = {
                Text(
                    stringResource(id = label)
                )
            },
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSlave(
    topBarTitle: Int, navController: NavController
) {
    LocalContext.current.applicationContext

    // Create a boolean variable
    // to store the display menu state
    var mDisplayMenu by remember { mutableStateOf(false) }

    // fetching local context
    val mContext = LocalContext.current

    TopAppBar(
        title = {
            Text(text = stringResource(id = topBarTitle))
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go Back")
            }
        },
        actions = {
            // Creating Icon button for dropdown menu
            IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                Icon(Icons.Default.MoreVert, "")
            }

            // Creating a dropdown menu
            DropdownMenu(expanded = mDisplayMenu, onDismissRequest = { mDisplayMenu = false }) {
                DropdownMenuItem(text = { Text(text = user?.displayName.toString()) },
                    onClick = {
                        Toast.makeText(mContext, user?.displayName.toString(), Toast.LENGTH_SHORT).show()
                    })

                // Creating dropdown menu item, on click
                // would create a Toast message
                DropdownMenuItem(text = {
                    Text(text = "Logout")
                }, onClick = {
                    Toast.makeText(mContext, "Logged out", Toast.LENGTH_SHORT).show()
                    Firebase.auth.signOut()
                    exitProcess(-1)
                })
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarMain(topBarTitle: Int) {

    LocalContext.current.applicationContext
    // Create a boolean variable
    // to store the display menu state
    var mDisplayMenu by remember { mutableStateOf(false) }

    // fetching local context
    val mContext = LocalContext.current
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(id = topBarTitle),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
//        navigationIcon = {
//            IconButton(onClick = { /* doSomething() */ }) {
//                Icon(
//                    imageVector = Icons.Filled.Search,
//                    contentDescription = "Search"
//                )
//            }
//        },
        actions = {
            // Creating Icon button for dropdown menu
            IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                Icon(Icons.Default.MoreVert, "")
            }

            // Creating a dropdown menu
            DropdownMenu(
                expanded = mDisplayMenu,
                onDismissRequest = { mDisplayMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = user?.displayName.toString()) },
                    onClick = {
                        Toast.makeText(
                            mContext,
                            "Logged in as ${user?.displayName.toString()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                )

                // Creating dropdown menu item, on click
                // would create a Toast message
                DropdownMenuItem(
                    text = {
                        Text(text = "Logout")
                    },
                    onClick = {
                        Toast.makeText(mContext, "Logout", Toast.LENGTH_SHORT).show()
                        Firebase.auth.signOut()
                        exitProcess(-1)
                    })
            }
        },
    )
}