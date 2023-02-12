package com.example.cuarsattendant.repositories


import com.example.cuarsattendant.models.FirstAid
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import com.google.firebase.storage.FirebaseStorage
import androidx.lifecycle.MutableLiveData

const val NOTES_COLLECTION_REF = "FirstAid"

class StorageRepository {
    var specimens: MutableLiveData<List<FirstAid>> = MutableLiveData<List<FirstAid>>()

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var storageReference = FirebaseStorage.getInstance().reference

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    private val TAG = "ReadAndWriteSnippets"
    private lateinit var database: DatabaseReference
    fun initializeDbRef() {
        database = Firebase.database.reference
    }

    fun user() = Firebase.auth.currentUser
    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    private val notesRef: CollectionReference = Firebase
        .firestore.collection(NOTES_COLLECTION_REF)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getUserFirstAid(
        userId: String,
    ): Flow<Resources<List<FirstAid>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null

        try {
            snapshotStateListener = notesRef
                .orderBy("timestamp")
                .whereEqualTo("userId", userId)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val notes = snapshot.toObjects(FirstAid::class.java)
                        Resources.Success(data = notes)
                    } else {
                        Resources.Error(throwable = e?.cause)
                    }
                    trySend(response)

                }


        } catch (e: Exception) {
            trySend(Resources.Error(e.cause))
            e.printStackTrace()
        }

        awaitClose {
            snapshotStateListener?.remove()
        }


    }

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

    fun deleteFirstAid(noteId: String, onComplete: (Boolean) -> Unit) {
        notesRef.document(noteId)
            .delete()
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }
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

    fun signOut() = Firebase.auth.signOut
}


sealed class Resources<T>(
    val data: T? = null,
    val throwable: Throwable? = null,
) {
    class Loading<T> : Resources<T>()
    class Success<T>(data: T?) : Resources<T>(data = data)
    class Error<T>(throwable: Throwable?) : Resources<T>(throwable = throwable)

}