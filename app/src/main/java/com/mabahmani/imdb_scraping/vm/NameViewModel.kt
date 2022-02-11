package com.mabahmani.imdb_scraping.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabahmani.domain.interactor.GetNameBioUseCase
import com.mabahmani.domain.interactor.GetNameDetailsUseCase
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.imdb_scraping.ui.main.name.state.NameBioUiState
import com.mabahmani.imdb_scraping.ui.main.name.state.NameDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor(
    private val getNameDetailsUseCase: GetNameDetailsUseCase,
    private val getNameBioUseCase: GetNameBioUseCase,
): ViewModel() {

    private val _nameDetailsUiState = MutableStateFlow<NameDetailUiState>(NameDetailUiState.Loading)
    val nameDetailsUiState: StateFlow<NameDetailUiState> = _nameDetailsUiState

    private val _nameBioUiState = MutableStateFlow<NameBioUiState>(NameBioUiState.Loading)
    val nameBioUiState: StateFlow<NameBioUiState> = _nameBioUiState

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

    fun launchGetNameBioUseCase(nameId: NameId){
        if (_nameBioUiState.value !is NameBioUiState.ShowNameBio){

            viewModelScope.launch {

                _nameBioUiState.emit(NameBioUiState.Loading)

                val nameBio = getNameBioUseCase(nameId)

                if (nameBio.isSuccess){
                    nameBio.getOrNull()?.let{
                        _nameBioUiState.emit(NameBioUiState.ShowNameBio(it))
                    }
                }

                else{
                    nameBio.exceptionOrNull()?.let{
                        when(it){
                            is UnknownHostException ->{
                                _nameBioUiState.emit(NameBioUiState.NetworkError)
                            }

                            else ->{
                                _nameBioUiState.emit(NameBioUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }

    }
}