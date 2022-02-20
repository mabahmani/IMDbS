package com.mabahmani.imdb_scraping.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mabahmani.domain.vo.HomeExtra
import com.mabahmani.domain.vo.common.Either
import com.mabahmani.domain.vo.common.Title
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.FragmentStreamProviderBinding
import com.mabahmani.imdb_scraping.util.toast
import timber.log.Timber

class StreamProviderFragment : Fragment() {


    lateinit var binding: FragmentStreamProviderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStreamProviderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = requireArguments().getSerializable("streamProviderTitles") as HomeExtra.StreamProvider

        val adapter = HomeMovieAdapter{
                playTrailer, model ->
            if (playTrailer){
                when (model.videoId?.validate()) {
                    is Either.Right -> {
                        findNavController().navigate(
                            R.id.videoDetailsFragment,
                            Bundle().apply {
                                putString("videoId", model.videoId?.value)
                                putString("title", model.title)
                            }
                        )
                    }

                    else -> {
                        requireContext().toast(getString(R.string.invalid_video_id))
                    }
                }
            }
            else{
                when (model.titleId?.validate()) {
                    is Either.Right -> {
                        findNavController().navigate(
                            R.id.titleDetailsFragment,
                            Bundle().apply {
                                putString("titleId", model.titleId?.value)
                                putString("title", model.title)
                            }
                        )
                    }

                    else -> {
                        requireContext().toast(getString(R.string.invalid_title_id))
                    }
                }
            }
        }

        binding.list.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.list.adapter = adapter

        adapter.submitList(item.movies)
    }
}