package com.example.meet12.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.meet12.model.Mahasiswa
import com.example.meet12.repository.MahasiswaRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailUiState {
    data class Success(val mahasiswa: Mahasiswa) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailViewModel(private val mhs: MahasiswaRepository) : ViewModel() {
    var detailUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    fun getMhsbyNim(nim: String) {
        viewModelScope.launch {
            detailUiState = DetailUiState.Loading
            detailUiState = try {
                val mahasiswa = mhs.getMahasiswabyNim(nim)
                DetailUiState.Success(mahasiswa)
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: HttpException) {
                DetailUiState.Error
            }
        }
    }

    fun updateMhs(nim: String, updatedMahasiswa: Mahasiswa) {
        viewModelScope.launch {
            try {
                mhs.updateMahasiswa(nim, updatedMahasiswa)
                detailUiState = DetailUiState.Success(updatedMahasiswa)
            } catch (e: IOException) {
                detailUiState = DetailUiState.Error
            } catch (e: HttpException) {
                detailUiState = DetailUiState.Error
            }
        }
    }

    fun deleteMhs(nim: String) {
        viewModelScope.launch {
            try {
                mhs.deleteMahasiswa(nim)
                detailUiState = DetailUiState.Loading
            } catch (e: IOException) {
                detailUiState = DetailUiState.Error
            } catch (e: HttpException) {
                detailUiState = DetailUiState.Error
            }
        }
    }
}
