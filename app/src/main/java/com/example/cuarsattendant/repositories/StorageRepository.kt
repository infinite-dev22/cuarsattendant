package com.example.cuarsattendant.repositories


import com.example.cuarsattendant.models.FirstAid
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val NOTES_COLLECTION_REF = "FirstAid"

class StorageRepository {

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    private lateinit var database: DatabaseReference
    private fun initializeDbRef() {
        database = Firebase.database.reference
    }

    fun user() = Firebase.auth.currentUser
    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    private val notesRef: CollectionReference = Firebase
        .firestore.collection(NOTES_COLLECTION_REF)

    fun getFirstAid(
        firstAidId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (FirstAid?) -> Unit
    ) {
        notesRef
            .document(firstAidId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it?.toObject(FirstAid::class.java))
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun addFirstAid(
        userId: String,
        name: String,
        description: String,
        onComplete: (Boolean) -> Unit,
    ) {
        initializeDbRef()
        val documentId = notesRef.document().id
        val note = FirstAid(
            userId,
            name,
            description
        )
        notesRef
            .document(documentId)
            .set(note)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }

        database.child(NOTES_COLLECTION_REF).child(documentId).setValue(note)
    }

    fun updateFirstAid(
        firstAidId: String,
        userId: String,
        name: String,
        description: String,
        onResult: (Boolean) -> Unit
    ) {
        val updateData = hashMapOf<String, Any>(
            "firstAidId" to firstAidId,
            "userId" to userId,
            "name" to name,
            "description" to description,
        )

        notesRef.document(firstAidId)
            .update(updateData)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }
    }

}