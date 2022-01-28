package com.mabahmani.imdb_scraping.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mabahmani.domain.interactor.*
import com.mabahmani.imdb_scraping.ui.main.search.state.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val advancedNameSearchUseCase: AdvancedNameSearchUseCase,
    private val advancedTitleSearchUseCase: AdvancedTitleSearchUseCase,
    private val getCalenderUseCase: GetCalenderUseCase,
    private val getCelebsUseCase: GetCelebsUseCase,
    private val getTitlesUseCase: GetTitlesUseCase,
    private val getEventsUseCase: GetEventsUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getKeywordsUseCase: GetKeywordsUseCase,
    private val searchTitlesByGenreUseCase: SearchTitlesByGenreUseCase,
    private val searchTitlesByKeywordsUseCase: SearchTitlesByKeywordsUseCase,
    private val suggestCelebUseCase: SuggestCelebUseCase,
    private val suggestTitleUseCase: SuggestTitleUseCase,
    private val suggestUseCase: SuggestUseCase
): ViewModel(){

    private val _celebsUiState = MutableStateFlow<CelebsUiState>(CelebsUiState.Loading)
    val celebsUiState: StateFlow<CelebsUiState> = _celebsUiState

    private val _eventsUiState = MutableStateFlow<EventsUiState>(EventsUiState.Loading)
    val eventsUiState: StateFlow<EventsUiState> = _eventsUiState

    private val _genresUiState = MutableStateFlow<GenresUiState>(GenresUiState.Loading)
    val genresUiState: StateFlow<GenresUiState> = _genresUiState

    private val _keywordsUiState = MutableStateFlow<KeywordsUiState>(KeywordsUiState.Loading)
    val keywordsUiState: StateFlow<KeywordsUiState> = _keywordsUiState

    private val _suggestionsUiState = MutableStateFlow<SuggestionsUiState>(SuggestionsUiState.Loading)
    val suggestionsUiState: StateFlow<SuggestionsUiState> = _suggestionsUiState

    private val _titlesUiState = MutableStateFlow<TitlesUiState>(TitlesUiState.Loading)
    val titlesUiState: StateFlow<TitlesUiState> = _titlesUiState

    fun launchAdvancedNameSearchUseCase(){

    }

    fun launchAdvancedTitleSearchUseCase(){

    }

    fun launchGetCalenderUseCase(){

    }

    fun launchGetCelebsUseCase(){
        if (_celebsUiState.value !is CelebsUiState.ShowSearchData){

            viewModelScope.launch {

                _celebsUiState.emit(CelebsUiState.Loading)

                _celebsUiState.emit(CelebsUiState.ShowSearchData(
                    getCelebsUseCase().flow.cachedIn(viewModelScope)
                ))

            }
        }
    }

    fun launchGetTitlesUseCase(){
        if (_titlesUiState.value !is TitlesUiState.ShowSearchData){

            viewModelScope.launch {

                _titlesUiState.emit(TitlesUiState.Loading)

                _titlesUiState.emit(TitlesUiState.ShowSearchData(
                    getTitlesUseCase().flow.cachedIn(viewModelScope)
                ))

            }
        }
    }

    fun launchGetEventsUseCase(){

    }

    fun launchGetGenresUseCase(){

        if (_genresUiState.value !is GenresUiState.ShowSearchData){

            viewModelScope.launch {
                _genresUiState.emit(GenresUiState.Loading)

                val genres = getGenresUseCase()

                if (genres.isSuccess){
                    genres.getOrNull()?.let{
                        _genresUiState.emit(GenresUiState.ShowSearchData(it))
                    }
                }

                else{
                    genres.exceptionOrNull()?.let{
                        when(it){
                            is UnknownHostException ->{
                                _genresUiState.emit(GenresUiState.NetworkError)
                            }

                            else ->{
                                _genresUiState.emit(GenresUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }

    }

    fun launchGetKeywordsUseCase(){
        if (_keywordsUiState.value !is KeywordsUiState.ShowSearchData){

            viewModelScope.launch {
                _keywordsUiState.emit(KeywordsUiState.Loading)

                val keywords = getKeywordsUseCase()

                if (keywords.isSuccess){
                    keywords.getOrNull()?.let{
                        _keywordsUiState.emit(KeywordsUiState.ShowSearchData(it))
                    }
                }

                else{
                    keywords.exceptionOrNull()?.let{
                        when(it){
                            is UnknownHostException ->{
                                _keywordsUiState.emit(KeywordsUiState.NetworkError)
                            }

                            else ->{
                                _keywordsUiState.emit(KeywordsUiState.Error(it.message.toString()))
                            }
                        }
                    }
                }
            }
        }
    }

    fun launchSearchTitlesByGenreUseCase(){

    }

    fun launchSearchTitlesByKeywordsUseCase(){

    }

    fun launchSuggestCelebUseCase(){

    }

    fun launchSuggestTitleUseCase(){

    }

    fun launchSuggestUseCaseUseCase(){

    }
}