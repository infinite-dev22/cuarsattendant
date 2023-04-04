package com.example.cuarsattendant.viewModels


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cuarsattendant.models.IncidentInformation
import com.example.cuarsattendant.sealed.IncidentDataState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings


class IncidentsViewModel : ViewModel() {
    val response: MutableState<IncidentDataState> = mutableStateOf(IncidentDataState.Empty)
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        val tempList = mutableListOf<IncidentInformation>()
        response.value = IncidentDataState.Loading
        FirebaseDatabase.getInstance()
            .reference
            .child("IncidentInformation")
            .orderByChild("name")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnap in snapshot.children) {
                        val incident = dataSnap.getValue(IncidentInformation::class.java)
                        if (incident != null)
                            tempList.add(incident)
                    }
                    response.value = IncidentDataState.Success(tempList)
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = IncidentDataState.Failure(error.message)
                }

            })
    }
}