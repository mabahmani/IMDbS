package com.mabahmani.imdb_scraping.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabahmani.domain.interactor.GetAnticipatedTrailersUseCase
import com.mabahmani.domain.interactor.GetPopularTrailersUseCase
import com.mabahmani.domain.interactor.GetRecentTrailersUseCase
import com.mabahmani.domain.interactor.GetTrendingTrailersUseCase
import com.mabahmani.imdb_scraping.ui.main.title.state.TitleTechnicalSpecsUiState
import com.mabahmani.imdb_scraping.ui.main.trailers.state.TrailersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class TrailerViewModel @Inject constructor(
    private val getAnticipatedTrailersUseCase: GetAnticipatedTrailersUseCase,
    private val getPopularTrailersUseCase: GetPopularTrailersUseCase,
    private val getRecentTrailersUseCase: GetRecentTrailersUseCase,
    private val getTrendingTrailersUseCase: GetTrendingTrailersUseCase
): ViewModel() {

    private val _trailersUiState = MutableStateFlow<TrailersUiState>(TrailersUiState.Loading)
    val trailersUiState: StateFlow<TrailersUiState> = _trailersUiState

    fun launchGetAnticipatedTrailersUseCase(){
        if (_trailersUiState.value !is TrailersUiState.ShowTrailersData){

            viewModelScope.launch {
                _trailersUiState.emit(TrailersUiState.Loading)

                val trailers = getAnticipatedTrailersUseCase()

                if (trailers.isSuccess){
                    trailers.getOrNull()?.let{
                        _trailersUiState.emit(TrailersUiState.ShowTrailersData(it))
                    }
                }

                else{
                    trailers.exceptionOrNull()?.let{
                        when(it){
                            is UnknownHostException ->{
                                _trailersUiState.emit(TrailersUiState.NetworkError)
                            }

                            is SocketTimeoutException ->{
                                _trailersUiState.emit(TrailersUiState.TimeOutError)
                            }

                            else ->{
                                _trailersUiState.emit(TrailersUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }
    }

    fun launchGetPopularTrailersUseCase(){
        if (_trailersUiState.value !is TrailersUiState.ShowTrailersData){

            viewModelScope.launch {
                _trailersUiState.emit(TrailersUiState.Loading)

                val trailers = getPopularTrailersUseCase()

                if (trailers.isSuccess){
                    trailers.getOrNull()?.let{
                        _trailersUiState.emit(TrailersUiState.ShowTrailersData(it))
                    }
                }

                else{
                    trailers.exceptionOrNull()?.let{
                        when(it){
                            is UnknownHostException ->{
                                _trailersUiState.emit(TrailersUiState.NetworkError)
                            }

                            is SocketTimeoutException ->{
                                _trailersUiState.emit(TrailersUiState.TimeOutError)
                            }

                            else ->{
                                _trailersUiState.emit(TrailersUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }
    }

    fun launchGetRecentTrailersUseCase(){
        if (_trailersUiState.value !is TrailersUiState.ShowTrailersData){

            viewModelScope.launch {
                _trailersUiState.emit(TrailersUiState.Loading)

                val trailers = getRecentTrailersUseCase()

                if (trailers.isSuccess){
                    trailers.getOrNull()?.let{
                        _trailersUiState.emit(TrailersUiState.ShowTrailersData(it))
                    }
                }

                else{
                    trailers.exceptionOrNull()?.let{
                        when(it){
                            is UnknownHostException ->{
                                _trailersUiState.emit(TrailersUiState.NetworkError)
                            }

                            is SocketTimeoutException ->{
                                _trailersUiState.emit(TrailersUiState.TimeOutError)
                            }

                            else ->{
                                _trailersUiState.emit(TrailersUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }
    }

    fun launchGetTrendingTrailersUseCase(){
        if (_trailersUiState.value !is TrailersUiState.ShowTrailersData){

            viewModelScope.launch {
                _trailersUiState.emit(TrailersUiState.Loading)

                val trailers = getTrendingTrailersUseCase()

                if (trailers.isSuccess){
                    trailers.getOrNull()?.let{
                        _trailersUiState.emit(TrailersUiState.ShowTrailersData(it))
                    }
                }

                else{
                    trailers.exceptionOrNull()?.let{
                        when(it){
                            is UnknownHostException ->{
                                _trailersUiState.emit(TrailersUiState.NetworkError)
                            }

                            is SocketTimeoutException ->{
                                _trailersUiState.emit(TrailersUiState.TimeOutError)
                            }

                            else ->{
                                _trailersUiState.emit(TrailersUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }
    }
}