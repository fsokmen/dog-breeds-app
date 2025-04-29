package com.example.myapplication.ui.gallery

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.usecase.FetchBreedGalleryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    val fetchBreedGallery: FetchBreedGalleryUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var uiState: MutableState<UiState> = mutableStateOf(UiState.Loading)
        private set

    private val breed = savedStateHandle.get<String>("breed").orEmpty()

    init {
        onRefresh()
    }

    fun onRefresh() {
        viewModelScope.launch {
            fetchBreedGallery()
        }
    }

    private suspend fun fetchBreedGallery() {
        try {
            uiState.value = UiState.Success(fetchBreedGallery.execute(breed))
        } catch (_: Exception) {
            uiState.value = UiState.Error
        }
    }


    sealed interface UiState {
        data object Loading : UiState
        data class Success(val urls: List<String>) : UiState
        data object Error : UiState
    }
}