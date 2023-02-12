package com.example.cuarsattendant.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cuarsattendant.models.FirstAid
import com.example.cuarsattendant.sealed.DataState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings


class FirstAidViewModel : ViewModel() {
    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        val tempList = mutableListOf<FirstAid>()
        response.value = DataState.Loading
        FirebaseDatabase.getInstance()
            .reference
            .child("FirstAid")
            .orderByChild("name")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnap in snapshot.children) {
                        val firstAid = dataSnap.getValue(FirstAid::class.java)
                        if (firstAid != null)
                            tempList.add(firstAid)
                    }
                    response.value = DataState.Success(tempList)
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = DataState.Failure(error.message)
                }

            })
    }
}