package com.mabahmani.imdb_scraping.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabahmani.domain.interactor.GetNewsDetailsUseCase
import com.mabahmani.domain.vo.common.NewsId
import com.mabahmani.imdb_scraping.ui.main.name.state.NameAwardUiState
import com.mabahmani.imdb_scraping.ui.main.news.state.NewsDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsDetailsUseCase: GetNewsDetailsUseCase
): ViewModel() {

    private val _newsDetailsUiState = MutableStateFlow<NewsDetailsUiState>(NewsDetailsUiState.Loading)
    val newsDetailsUiState: StateFlow<NewsDetailsUiState> = _newsDetailsUiState
    
    fun launchGetNewsDetailsUseCase(newsId: NewsId){
        viewModelScope.launch {
            _newsDetailsUiState.emit(NewsDetailsUiState.Loading)

            val home = getNewsDetailsUseCase(newsId)

            if (home.isSuccess){

                home.getOrNull()?.let {
                    _newsDetailsUiState.emit(NewsDetailsUiState.ShowNewsDetails(it))
                }

            }
            else{
                home.exceptionOrNull()?.let {
                    when(it){
                        is UnknownHostException ->{
                            _newsDetailsUiState.emit(NewsDetailsUiState.NetworkError)
                        }

                        is SocketTimeoutException ->{
                            _newsDetailsUiState.emit(NewsDetailsUiState.TimeOutError)
                        }

                        else ->{
                            _newsDetailsUiState.emit(NewsDetailsUiState.Error(it.message.toString()))
                        }
                    }
                }
            }
        }

    }

}