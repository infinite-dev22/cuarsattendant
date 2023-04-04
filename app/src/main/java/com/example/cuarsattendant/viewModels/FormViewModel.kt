package com.example.cuarsattendant.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cuarsattendant.models.FirstAid
import com.example.cuarsattendant.repositories.StorageRepository
import com.google.firebase.auth.FirebaseUser

class DetailViewModel(
    private val repository: StorageRepository = StorageRepository(),
) : ViewModel() {
    var detailUiState by mutableStateOf(DetailUiState())
        private set

    private val hasUser: Boolean
        get() = repository.hasUser()

    private val user: FirebaseUser?
        get() = repository.user()

    fun onNameChange(name: String) {
        detailUiState = detailUiState.copy(name = name)
    }

    fun onDescriptionChange(description: String) {
        detailUiState = detailUiState.copy(description = description)
    }

    fun addGenFirstAid() {
        if (hasUser) {
            repository.addFirstAid(
                userId = user!!.uid,
                name = detailUiState.name,
                description = detailUiState.description
            ) {
                detailUiState = detailUiState.copy(firstAidAddedStatus = it)
            }
            resetState()
        }
    }

    fun setEditFields(FirstAid: FirstAid) {
        detailUiState = detailUiState.copy(
            name = FirstAid.name,
            description = FirstAid.description,
        )
    }

    fun resetState() {
        detailUiState = detailUiState.copy(name = "")
        detailUiState = detailUiState.copy(description = "")
    }


}

data class DetailUiState(
    val userId: String = "",
    val name: String = "",
    val description: String = "",
    val firstAidAddedStatus: Boolean = false,
    val updateFirstAidStatus: Boolean = false,
    val selectedFirstAid: FirstAid? = null,
)
