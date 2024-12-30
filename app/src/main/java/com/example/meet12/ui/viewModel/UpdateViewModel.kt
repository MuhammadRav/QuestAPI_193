package com.example.meet12.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meet12.repository.MahasiswaRepository
import com.example.meet12.ui.view.DestinasiUpdate
import kotlinx.coroutines.launch

class UpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val mhs: MahasiswaRepository
) : ViewModel() {
    var UpdateUiState by mutableStateOf(InsertUiState())
        private set

    private val _nim: String = checkNotNull(savedStateHandle[DestinasiUpdate.NIM])

    init {
        viewModelScope.launch {
            UpdateUiState = mhs.getMahasiswabyNim(_nim)
                .toUiStateMhs()
        }
    }
    fun updateInsertMhsState(insertUiEvent: InsertUiEvent){
        UpdateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateMhs(){
        viewModelScope.launch {
            try {
                mhs.updateMahasiswa(_nim, UpdateUiState.insertUiEvent.toMhs())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}