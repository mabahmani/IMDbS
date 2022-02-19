package com.mabahmani.imdb_scraping.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mabahmani.data.util.Constants
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.BottomsheetHomeSettingBinding
import com.mabahmani.imdb_scraping.ui.main.home.state.SettingUiState
import com.mabahmani.imdb_scraping.vm.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeSettingBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: BottomsheetHomeSettingBinding

    private val viewModel: SettingViewModel by viewModels()

    private var fromUser: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomsheetHomeSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeSettingStateObserver()
    }

    private fun observeRadioGroup() {

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (fromUser){
                when (checkedId) {
                    R.id.radioDay -> {
                        viewModel.setThemeMode(AppCompatDelegate.MODE_NIGHT_NO)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }

                    R.id.radioNight -> {
                        viewModel.setThemeMode(AppCompatDelegate.MODE_NIGHT_YES)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }

                    R.id.radioNightAutoBattery -> {
                        viewModel.setThemeMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                    }

                    R.id.radioFollowSystem -> {
                        viewModel.setThemeMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                }

                dismiss()
            }
        }
    }

    private fun observeSettingStateObserver() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.settingUiState.collect {
                    handleSettingStates(it)
                    observeRadioGroup()
                }
            }
        }
    }

    private fun handleSettingStates(state: SettingUiState) {
        fromUser = false

        when (state) {
            is SettingUiState.GetThemeMode -> {
                when (state.mode) {
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> binding.radioGroup.check(R.id.radioFollowSystem)
                    AppCompatDelegate.MODE_NIGHT_YES -> binding.radioGroup.check(R.id.radioNight)
                    AppCompatDelegate.MODE_NIGHT_NO -> binding.radioGroup.check(R.id.radioDay)
                    AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY -> binding.radioGroup.check(R.id.radioNightAutoBattery)
                }
            }
        }

        fromUser = true
    }

}