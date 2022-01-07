package com.mabahmani.imdb_scraping.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mabahmani.domain.vo.HomeExtra
import com.mabahmani.domain.vo.common.Title
import com.mabahmani.imdb_scraping.databinding.FragmentStreamProviderBinding
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

        }

        binding.list.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.list.adapter = adapter

        adapter.submitList(item.movies)
    }
}