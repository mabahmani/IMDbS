package com.mabahmani.imdb_scraping.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabahmani.domain.interactor.*
import com.mabahmani.imdb_scraping.ui.main.charts.state.ChartBoxOfficeUiState
import com.mabahmani.imdb_scraping.ui.main.charts.state.ChartUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val getBottom100MoviesUseCase: GetBottom100MoviesUseCase,
    private val getBoxOfficeUseCase: GetBoxOfficeUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getPopularTvShowsUseCase: GetPopularTvShowsUseCase,
    private val getTop250MoviesUseCase: GetTop250MoviesUseCase,
    private val getTop250TvShowsUseCase: GetTop250TvShowsUseCase
) : ViewModel() {

    private val _chartsUiState = MutableStateFlow<ChartUiState>(ChartUiState.Loading)
    val chartsUiState: StateFlow<ChartUiState> = _chartsUiState

    private val _boxOfficeUiState = MutableStateFlow<ChartBoxOfficeUiState>(ChartBoxOfficeUiState.Loading)
    val boxOfficeUiState: StateFlow<ChartBoxOfficeUiState> = _boxOfficeUiState

    fun launchGetBottom100MoviesUseCase(){
        if (_chartsUiState.value !is ChartUiState.ShowChartsData){

            viewModelScope.launch {
                _chartsUiState.emit(ChartUiState.Loading)

                val charts = getBottom100MoviesUseCase()

                if (charts.isSuccess){
                    charts.getOrNull()?.let{
                        _chartsUiState.emit(ChartUiState.ShowChartsData(it))
                    }
                }

                else{
                    charts.exceptionOrNull()?.let{
                        when(it){
                            is UnknownHostException ->{
                                _chartsUiState.emit(ChartUiState.NetworkError)
                            }

                            is SocketTimeoutException ->{
                                _chartsUiState.emit(ChartUiState.TimeOutError)
                            }

                            else ->{
                                _chartsUiState.emit(ChartUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }
    }

    fun launchGetBoxOfficeUseCase(){
        if (_boxOfficeUiState.value !is ChartBoxOfficeUiState.ShowBoxOfficeData){

            viewModelScope.launch {
                _boxOfficeUiState.emit(ChartBoxOfficeUiState.Loading)

                val charts = getBoxOfficeUseCase()

                if (charts.isSuccess){
                    charts.getOrNull()?.let{
                        _boxOfficeUiState.emit(ChartBoxOfficeUiState.ShowBoxOfficeData(it))
                    }
                }

                else{
                    charts.exceptionOrNull()?.let{
                        when(it){
                            is UnknownHostException ->{
                                _boxOfficeUiState.emit(ChartBoxOfficeUiState.NetworkError)
                            }

                            is SocketTimeoutException ->{
                                _boxOfficeUiState.emit(ChartBoxOfficeUiState.TimeOutError)
                            }

                            else ->{
                                _boxOfficeUiState.emit(ChartBoxOfficeUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }
    }

    fun launchGetPopularMoviesUseCase(){
        if (_chartsUiState.value !is ChartUiState.ShowChartsData){

            viewModelScope.launch {
                _chartsUiState.emit(ChartUiState.Loading)

                val charts = getPopularMoviesUseCase()

                if (charts.isSuccess){
                    charts.getOrNull()?.let{
                        _chartsUiState.emit(ChartUiState.ShowChartsData(it))
                    }
                }

                else{
                    charts.exceptionOrNull()?.let{
                        when(it){
                            is UnknownHostException ->{
                                _chartsUiState.emit(ChartUiState.NetworkError)
                            }

                            is SocketTimeoutException ->{
                                _chartsUiState.emit(ChartUiState.TimeOutError)
                            }

                            else ->{
                                _chartsUiState.emit(ChartUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }
    }

    fun launchGetPopularTvShowsUseCase(){
        if (_chartsUiState.value !is ChartUiState.ShowChartsData){

            viewModelScope.launch {
                _chartsUiState.emit(ChartUiState.Loading)

                val charts = getPopularTvShowsUseCase()

                if (charts.isSuccess){
                    charts.getOrNull()?.let{
                        _chartsUiState.emit(ChartUiState.ShowChartsData(it))
                    }
                }

                else{
                    charts.exceptionOrNull()?.let{
                        when(it){
                            is UnknownHostException ->{
                                _chartsUiState.emit(ChartUiState.NetworkError)
                            }

                            is SocketTimeoutException ->{
                                _chartsUiState.emit(ChartUiState.TimeOutError)
                            }

                            else ->{
                                _chartsUiState.emit(ChartUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }
    }

    fun launchGetTop250MoviesUseCase(){
        if (_chartsUiState.value !is ChartUiState.ShowChartsData){

            viewModelScope.launch {
                _chartsUiState.emit(ChartUiState.Loading)

                val charts = getTop250MoviesUseCase()

                if (charts.isSuccess){
                    charts.getOrNull()?.let{
                        _chartsUiState.emit(ChartUiState.ShowChartsData(it))
                    }
                }

                else{
                    charts.exceptionOrNull()?.let{
                        when(it){
                            is UnknownHostException ->{
                                _chartsUiState.emit(ChartUiState.NetworkError)
                            }

                            is SocketTimeoutException ->{
                                _chartsUiState.emit(ChartUiState.TimeOutError)
                            }

                            else ->{
                                _chartsUiState.emit(ChartUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }
    }

    fun launchGetTop250TvShowsUseCase(){
        if (_chartsUiState.value !is ChartUiState.ShowChartsData){

            viewModelScope.launch {
                _chartsUiState.emit(ChartUiState.Loading)

                val charts = getTop250TvShowsUseCase()

                if (charts.isSuccess){
                    charts.getOrNull()?.let{
                        _chartsUiState.emit(ChartUiState.ShowChartsData(it))
                    }
                }

                else{
                    charts.exceptionOrNull()?.let{
                        when(it){
                            is UnknownHostException ->{
                                _chartsUiState.emit(ChartUiState.NetworkError)
                            }

                            is SocketTimeoutException ->{
                                _chartsUiState.emit(ChartUiState.TimeOutError)
                            }

                            else ->{
                                _chartsUiState.emit(ChartUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }
    }
}