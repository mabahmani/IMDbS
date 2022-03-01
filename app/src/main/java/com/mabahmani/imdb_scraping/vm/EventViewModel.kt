package com.mabahmani.imdb_scraping.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabahmani.domain.interactor.GetEventDetailsUseCase
import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.imdb_scraping.ui.main.event.state.EventDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val getEventDetailsUseCase: GetEventDetailsUseCase
): ViewModel() {

    private val _eventDetailsUiState = MutableStateFlow<EventDetailsUiState>(EventDetailsUiState.Loading)
    val eventDetailsUiState: StateFlow<EventDetailsUiState> = _eventDetailsUiState


    fun launchGetEventDetailsUseCase(eventId: EventId, year: Int){
        viewModelScope.launch {
            _eventDetailsUiState.emit(EventDetailsUiState.Loading)

            val eventDetails = getEventDetailsUseCase(eventId, year)

            if (eventDetails.isSuccess){

                eventDetails.getOrNull()?.let {
                    _eventDetailsUiState.emit(EventDetailsUiState.ShowEventDetailsData(it))
                }

            }
            else{
                eventDetails.exceptionOrNull()?.let {
                    when(it){
                        is UnknownHostException ->{
                            _eventDetailsUiState.emit(EventDetailsUiState.NetworkError)
                        }

                        else ->{
                            _eventDetailsUiState.emit(EventDetailsUiState.Error(it.message.toString()))
                        }
                    }
                }
            }
        }
    }
}