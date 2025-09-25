package com.example.app.features.dollar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.domain.model.DollarModel
import com.example.app.domain.usecases.FetchDollarUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DollarViewModel(
    val fetchDollarUseCase: FetchDollarUseCase
) : ViewModel() {

    sealed class DollarUIState {
        object Loading : DollarUIState()
        class Error(val message: String) : DollarUIState()
        class Success(val data: DollarModel) : DollarUIState()
    }

    private val _uiState = MutableStateFlow<DollarUIState>(DollarUIState.Loading)
    val uiState: StateFlow<DollarUIState> = _uiState.asStateFlow()

    init {
        getDollar()
    }

    fun getDollar() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                fetchDollarUseCase.invoke().collect { data ->
                    _uiState.value = DollarUIState.Success(data)
                }
            } catch (e: Exception) {
                _uiState.value = DollarUIState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

