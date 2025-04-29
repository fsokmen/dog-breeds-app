package com.example.myapplication.ui.breeds

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.usecase.FetchAllBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(
    val fetchBreeds: FetchAllBreedsUseCase,
) : ViewModel() {

    var uiState: MutableState<UiState> = mutableStateOf(UiState.Loading)
        private set

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event.asSharedFlow()

    init {
        onRefresh()
    }

    fun onRefresh() {
        viewModelScope.launch {
            fetchBreeds()
        }
    }

    fun onBreedClicked(breed: String) {
        viewModelScope.launch {
            _event.emit(Event.NavigateToGallery(breed))
        }
    }

    private suspend fun fetchBreeds() {
        try {
            uiState.value = UiState.Loading
            uiState.value = UiState.Success(fetchBreeds.execute())
        } catch (_: Exception) {
            uiState.value = UiState.Error
        }
    }


    sealed interface UiState {
        data object Loading : UiState
        data class Success(val breeds: List<String>) : UiState
        data object Error : UiState
    }

    sealed interface Event {
        data class NavigateToGallery(val breed: String) : Event
    }
}