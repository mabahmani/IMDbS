package com.mabahmani.imdb_scraping.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabahmani.domain.interactor.GetNameDetailsUseCase
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.imdb_scraping.ui.main.name.state.NameDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor(
    private val getNameDetailsUseCase: GetNameDetailsUseCase
): ViewModel() {

    private val _nameDetailsUiState = MutableStateFlow<NameDetailUiState>(NameDetailUiState.Loading)
    val nameDetailsUiState: StateFlow<NameDetailUiState> = _nameDetailsUiState

    fun launchGetNameDetailsUseCase(nameId: NameId){
        if (_nameDetailsUiState.value !is NameDetailUiState.ShowNameDetails){

            viewModelScope.launch {

                _nameDetailsUiState.emit(NameDetailUiState.Loading)

                val nameDetails = getNameDetailsUseCase(nameId)

                if (nameDetails.isSuccess){
                    nameDetails.getOrNull()?.let{
                        _nameDetailsUiState.emit(NameDetailUiState.ShowNameDetails(it))
                    }
                }

                else{
                    nameDetails.exceptionOrNull()?.let{
                        when(it){
                            is UnknownHostException ->{
                                _nameDetailsUiState.emit(NameDetailUiState.NetworkError)
                            }

                            else ->{
                                _nameDetailsUiState.emit(NameDetailUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }

    }
}