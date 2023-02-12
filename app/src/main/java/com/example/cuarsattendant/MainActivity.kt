package com.example.cuarsattendant

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cuarsattendant.navigation.NavigationMap
import com.example.cuarsattendant.ui.theme.CuarsAttendantTheme
import com.example.cuarsattendant.viewModels.DetailViewModel
import com.example.cuarsattendant.viewModels.FirstAidViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (user == null) {
            signIn()
        }
        setContent {
            CuarsAttendantTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val detailViewModel = viewModel(modelClass = DetailViewModel::class.java)
                    val FirstAidViewModel = viewModel(modelClass = FirstAidViewModel::class.java)
                    val firebaseDatabase = FirebaseDatabase.getInstance();
                    val databaseReference = firebaseDatabase.getReference("IncidentInformation");

                    NavigationMap(detailViewModel)
                }
            }
        }
    }

     fun signIn () {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
//            AuthUI.IdpConfig.PhoneBuilder().build(),
//            AuthUI.IdpConfig.GoogleBuilder().build(),
        )

        val signinIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signinIntent)
    }

    val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ){
            res -> this.signInResult(res)
    }

    fun signInResult(result: FirebaseAuthUIAuthenticationResult){
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK){
            user = FirebaseAuth.getInstance().currentUser
            Toast.makeText(this.baseContext, "SignedI As: " + user?.displayName, Toast.LENGTH_SHORT)
        } else {
            Toast.makeText(this.baseContext, "Error log In: " + response?.error?.errorCode, Toast.LENGTH_SHORT)
        }
    }
}