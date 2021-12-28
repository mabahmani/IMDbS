package com.mabahmani.imdb_scraping

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabahmani.domain.interactor.GetHomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase
):ViewModel() {

    fun getHome(){
        viewModelScope.launch(Dispatchers.IO) {
            val homeResult = getHomeUseCase()
        }
    }

}