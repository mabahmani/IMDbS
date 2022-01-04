package com.mabahmani.imdb_scraping.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabahmani.domain.interactor.GetHomeUseCase
import com.mabahmani.imdb_scraping.ui.main.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase
):ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init {
        launchHomeUseCase()
    }

    fun launchHomeUseCase(){
        viewModelScope.launch {
            _homeUiState.emit(HomeUiState.Loading)

            val home = getHomeUseCase()

            if (home.isSuccess){

                home.getOrNull()?.let {
                    _homeUiState.emit(HomeUiState.ShowData(it))
                }

            }
            else{
                home.exceptionOrNull()?.let {
                    when(it){
                        is UnknownHostException ->{
                            _homeUiState.emit(HomeUiState.ShowNetworkError)
                        }

                        else ->{
                            _homeUiState.emit(HomeUiState.ShowError(it.message.toString()))
                        }
                    }
                }
            }
        }
    }

}