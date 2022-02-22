package com.mabahmani.imdb_scraping.ui.main.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mabahmani.domain.vo.EventDetails
import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.imdb_scraping.databinding.FragmentEventDetailsBinding
import com.mabahmani.imdb_scraping.ui.main.event.state.EventDetailsUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.vm.EventViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class EventDetailsFragment: Fragment() {

    lateinit var binding: FragmentEventDetailsBinding
    private val viewModel: EventViewModel by viewModels()
    lateinit var eventId: EventId

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkArguments()
        setupAppBar()
        watchYearInput()
        initEventDetailsStateObserver()
    }

    private fun setupAppBar() {
        binding.appBar.getTitleView()?.text = requireArguments().getString("eventName", "")
    }

    private fun checkArguments() {
        eventId = EventId(requireArguments().getString("eventId", ""))
    }

    private fun watchYearInput() {
        binding.yearInput.doAfterTextChanged {
            if (it?.length == 4){
                try {
                    viewModel.launchGetEventDetailsUseCase(eventId, it.toString().toInt())
                }catch (ex: Exception){
                    ex.printStackTrace()
                }
            }
        }
    }

    private fun initEventDetailsStateObserver() {

        binding.yearInput.setText((Calendar.getInstance().get(Calendar.YEAR) - 1).toString())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.eventDetailsUiState.collect{
                    handleEventDetailsStates(it)
                }
            }
        }
    }

    private fun handleEventDetailsStates(state: EventDetailsUiState) {
        when(state){
            is EventDetailsUiState.Loading ->{
                showLoading()
            }
            is EventDetailsUiState.ShowEventDetailsData ->{
                showEventDetails(state.eventDetails)
            }
            is EventDetailsUiState.Error ->{
                showError(state.message)
            }
            is EventDetailsUiState.NetworkError ->{
                showNetworkError()
            }
        }
    }

    private fun showEventDetails(eventDetails: EventDetails) {
        val adapter = EventAwardAdapter()

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter

        adapter.submitList(eventDetails.awards)

        if (eventDetails.eventCaption.isNotEmpty()){
            binding.subtitleView.visibility = View.VISIBLE
            binding.subtitleView.text = eventDetails.eventCaption
        }

        else{
            binding.subtitleView.visibility = View.GONE
        }

        hideLoading()
    }

    private fun showNetworkError() {
        requireContext().showNetworkConnectionError {
            viewModel.launchGetEventDetailsUseCase(eventId, binding.yearInput.text.toString().toInt())
        }
    }

    private fun showError(message: String) {
        requireContext().showUnexpectedError()
        viewModel.launchGetEventDetailsUseCase(eventId, binding.yearInput.text.toString().toInt())
    }

    private fun showLoading() {
        binding.list.visibility = View.GONE
        binding.shimmer.startShimmer()
        binding.shimmer.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.list.visibility = View.VISIBLE
        binding.shimmer.visibility = View.GONE
        binding.shimmer.stopShimmer()
    }
}