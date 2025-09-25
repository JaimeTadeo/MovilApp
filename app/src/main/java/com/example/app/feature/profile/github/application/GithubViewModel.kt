package com.example.app.feature.profile.github.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.domain.model.UserModel
import com.example.app.domain.usecases.FindByNickname
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GithubViewModel(
    private val useCase: FindByNickname
): ViewModel() {
    sealed class GithubStateUI {
        object Init: GithubStateUI()
        object Loading: GithubStateUI()
        class Error(val message: String): GithubStateUI()
        class Success(val github: UserModel): GithubStateUI()
    }

    private val _state = MutableStateFlow<GithubStateUI>(GithubStateUI.Init)
    val state : StateFlow<GithubStateUI> = _state.asStateFlow()

    fun fetchAlias(nickname: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = GithubStateUI.Loading
            val result = useCase.invoke(nickname)
            result.fold(
                onSuccess = { user ->
                    _state.value = GithubStateUI.Success(user)
                },
                onFailure = { error ->
                    _state.value = GithubStateUI.Error(error.message ?: "Error desconocido")
                }
            )
        }
    }
}