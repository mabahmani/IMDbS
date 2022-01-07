package com.mabahmani.imdb_scraping.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabahmani.domain.interactor.GetHomeExtraUseCase
import com.mabahmani.domain.interactor.GetHomeUseCase
import com.mabahmani.imdb_scraping.ui.main.home.state.HomeExtraUiState
import com.mabahmani.imdb_scraping.ui.main.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase,
    private val getHomeExtraUseCase: GetHomeExtraUseCase,
):ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    private val _homeExtraUiState = MutableStateFlow<HomeExtraUiState>(HomeExtraUiState.Loading)
    val homeExtraUiState: StateFlow<HomeExtraUiState> = _homeExtraUiState

    init {
        launchHomeUseCase()
        launchHomeExtraUseCase()
    }

    fun launchHomeUseCase(){
        viewModelScope.launch {
            _homeUiState.emit(HomeUiState.Loading)

            val home = getHomeUseCase()

            if (home.isSuccess){

                home.getOrNull()?.let {
                    _homeUiState.emit(HomeUiState.ShowHomeData(it))
                }

            }
            else{
                home.exceptionOrNull()?.let {
                    when(it){
                        is UnknownHostException ->{
                            _homeUiState.emit(HomeUiState.NetworkError)
                        }

                        else ->{
                            _homeUiState.emit(HomeUiState.Error(it.message.toString()))
                        }
                    }
                }
            }
        }
    }

    fun launchHomeExtraUseCase(){

        viewModelScope.launch {
            _homeExtraUiState.emit(HomeExtraUiState.Loading)

            val homeExtra = getHomeExtraUseCase()

            if (homeExtra.isSuccess){

                homeExtra.getOrNull()?.let {
                    _homeExtraUiState.emit(HomeExtraUiState.ShowHomeExtraData(it))
                }

            }
            else{
                homeExtra.exceptionOrNull()?.let {
                    when(it){
                        is UnknownHostException ->{
                            _homeExtraUiState.emit(HomeExtraUiState.NetworkError)
                        }

                        else ->{
                            _homeExtraUiState.emit(HomeExtraUiState.Error(it.message.toString()))
                        }
                    }
                }
            }
        }
    }

}