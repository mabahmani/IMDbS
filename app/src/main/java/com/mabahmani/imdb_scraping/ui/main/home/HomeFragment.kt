package com.mabahmani.imdb_scraping.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.mabahmani.domain.vo.Home
import com.mabahmani.domain.vo.HomeExtra
import com.mabahmani.imdb_scraping.databinding.FragmentHomeBinding
import com.mabahmani.imdb_scraping.ui.custom.RoundedPagerIndicatorDecoration
import com.mabahmani.imdb_scraping.ui.main.home.state.HomeExtraUiState
import com.mabahmani.imdb_scraping.ui.main.home.state.HomeUiState
import com.mabahmani.imdb_scraping.util.showNetworkConnectionError
import com.mabahmani.imdb_scraping.vm.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import android.graphics.Typeface

import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mabahmani.domain.vo.common.*
import com.mabahmani.domain.vo.enum.HomeMediaType
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.util.showUnexpectedError
import com.mabahmani.imdb_scraping.util.toast
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeScroll()
        initHomeStateObserver()
        initHomeExtraStateObserver()
    }

    private fun observeScroll() {
        binding.appBar.setBackgroundAlpha(0)
        binding.nestedParent.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY <= 255) {
                binding.appBar.setBackgroundAlpha(scrollY)
            } else {
                binding.appBar.setBackgroundAlpha(255)
            }
        })
    }

    private fun initHomeStateObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.homeUiState.collect {
                    handleHomeStates(it)
                }
            }
        }
    }

    private fun initHomeExtraStateObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.homeExtraUiState.collect {
                    handleHomeExtraStates(it)
                }
            }
        }
    }

    private fun handleHomeStates(state: HomeUiState) {

        when (state) {
            is HomeUiState.Loading -> {
                showHomeLoading()
            }
            is HomeUiState.ShowHomeData -> {
                showTrailers(state.home.trailers)
                showFeaturedToday(state.home.featuredToday)
                showImdbOriginals(state.home.imdbOriginals)
                showEditorPicks(state.home.editorPicks)
                showBoxOffice(state.home.boxOffice)
                showNews(state.home.news)
            }
            is HomeUiState.Error -> {
                showHomeError(state.message)
            }
            is HomeUiState.NetworkError -> {
                showHomeNetworkError()
            }
        }
    }

    private fun handleHomeExtraStates(state: HomeExtraUiState) {

        when (state) {
            is HomeExtraUiState.Loading -> {
                showHomeExtraLoading()
            }
            is HomeExtraUiState.ShowHomeExtraData -> {
                showFanFavorites(state.homeExtra.fanPicksTitles)
                showStreamProviders(state.homeExtra.streamingTitles)
                showInTheaters(state.homeExtra.showTimesTitles)
                showComingSoon(state.homeExtra.comingSoonTitles)
                showBornToday(state.homeExtra.bornTodayList)
            }
            is HomeExtraUiState.Error -> {
                showHomeExtraError(state.message)
            }
            is HomeExtraUiState.NetworkError -> {
                showHomeExtraNetworkError()
            }
        }
    }

    private fun showBornToday(bornTodayList: List<HomeExtra.BornToday>) {

        binding.bornTodayTitleWidget.setSubtitle(
            String.format(
                resources.getString(R.string.born_today_subtitle),
                SimpleDateFormat("MMMM dd", Locale.US).format(
                    Date()
                )
            )
        )

        val adapter = HomeBornTodayAdapter {
            when (it.nameId.validate()) {
                is Either.Right -> {
                    findNavController().navigate(R.id.nameDetailsFragment,
                        Bundle().apply {
                            putString("nameId", it.nameId.value)
                            putString("name", it.name)
                        }
                    )
                }

                else -> {
                    requireContext().toast(getString(R.string.invalid_name_id))
                }
            }

        }

        binding.bornTodayList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.bornTodayList.adapter = adapter
        adapter.submitList(bornTodayList)


        binding.bornTodayShimmer.visibility = View.GONE
        binding.bornTodayShimmer.stopShimmer()
        binding.bornTodayParent.visibility = View.VISIBLE
    }


    private fun showNews(news: List<News>) {

        val adapter = HomeNewsAdapter {
            findNavController().navigate(R.id.newsDetailsFragment,
                Bundle().apply {
                    putString("newsId", it.newsId.value)
                }
            )
        }

        binding.newsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.newsList.adapter = adapter
        adapter.submitList(news)


        binding.newsShimmer.visibility = View.GONE
        binding.newsShimmer.stopShimmer()
        binding.newsParent.visibility = View.VISIBLE
    }


    private fun showComingSoon(comingSoonTitles: List<Title>) {
        val sourceFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val targetFormat = SimpleDateFormat("MMM dd", Locale.US)


        val adapter = HomeMediaAdapter {

        }

        binding.comingSoonList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.comingSoonList.adapter = adapter

        adapter.submitList(comingSoonTitles.map {

            var releaseDate: String? = null

            try {
                releaseDate = targetFormat.format(
                    sourceFormat.parse(
                        String.format(
                            "%s-%s-%s",
                            it.releaseYear,
                            it.releaseMonth,
                            it.releaseDay
                        )
                    )
                )
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            Home.Media(
                it.title.orEmpty(),
                it.videoRuntime.orEmpty(),
                HomeMediaType.VIDEO,
                it.videoPreview ?: Image(""),
                it.videoId?.value.orEmpty(),
                releaseDate
            )
        })


        binding.comingSoonShimmer.visibility = View.GONE
        binding.comingSoonShimmer.stopShimmer()
        binding.comingSoonParent.visibility = View.VISIBLE
    }

    private fun showFanFavorites(fanPicksTitles: List<Title>) {
        val adapter = HomeMovieAdapter {
            when (it.titleId?.validate()) {
                is Either.Right -> {
                    findNavController().navigate(R.id.titleDetailsFragment,
                        Bundle().apply {
                            putString("titleId", it.titleId?.value)
                            putString("title", it.title)
                        }
                    )
                }

                else -> {
                    requireContext().toast(getString(R.string.invalid_title_id))
                }
            }
        }

        binding.fanFavoritesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.fanFavoritesList.adapter = adapter
        adapter.submitList(fanPicksTitles)


        binding.fanFavoritesShimmer.visibility = View.GONE
        binding.fanFavoritesShimmer.stopShimmer()
        binding.fanFavoritesParent.visibility = View.VISIBLE
    }

    private fun showInTheaters(inTheaters: List<Title>) {
        val adapter = HomeMovieAdapter {

        }

        binding.inTheatersList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.inTheatersList.adapter = adapter
        adapter.submitList(inTheaters)

        binding.inTheatersShimmer.visibility = View.GONE
        binding.inTheatersShimmer.stopShimmer()
        binding.inTheatersParent.visibility = View.VISIBLE
    }

    private fun showStreamProviders(streamProviders: List<HomeExtra.StreamProvider>) {

        binding.streamProvidersViewPager.apply {
            isUserInputEnabled = false
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        val streamProviderAdapter = StreamProviderFragmentStateAdapter(this, streamProviders)

        binding.streamProvidersViewPager.adapter = streamProviderAdapter


        TabLayoutMediator(
            binding.streamProvidersTabLayout,
            binding.streamProvidersViewPager
        ) { tab, position ->
            tab.text = streamProviders[position].name
        }.attach()


        val vg = binding.streamProvidersTabLayout.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup
            val tabChildsCount = vgTab.childCount
            for (i in 0 until tabChildsCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {
                    tabViewChild.setTypeface(
                        Typeface.createFromAsset(
                            resources.assets, resources.getString(
                                R.string.font_medium
                            )
                        ), Typeface.NORMAL
                    )
                }
            }
        }

        binding.streamProvidersShimmer.visibility = View.GONE
        binding.streamProvidersShimmer.stopShimmer()
        binding.streamProvidersParent.visibility = View.VISIBLE
    }

    private fun showHomeNetworkError() {

        requireContext().showNetworkConnectionError {
            viewModel.launchHomeUseCase()
        }
    }

    private fun showHomeExtraNetworkError() {

        requireContext().showNetworkConnectionError {
            viewModel.launchHomeExtraUseCase()
        }
    }

    private fun showHomeError(message: String) {
        requireContext().showUnexpectedError()
        viewModel.launchHomeUseCase()
    }

    private fun showHomeExtraError(message: String) {
        requireContext().showUnexpectedError()
        viewModel.launchHomeExtraUseCase()
    }

    private fun showTrailers(trailers: List<Trailer>) {
        val adapter = HomeTrailerAdapter {

        }
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.trailersList)
        binding.trailersList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.trailersList.addItemDecoration(RoundedPagerIndicatorDecoration())
        binding.trailersList.adapter = adapter

        adapter.submitList(trailers)

        binding.trailersShimmer.visibility = View.GONE
        binding.trailersShimmer.stopShimmer()
        binding.trailersParent.visibility = View.VISIBLE
    }

    private fun showFeaturedToday(medias: List<Home.Media>) {
        val adapter = HomeMediaAdapter {

        }

        binding.featuredTodayList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.featuredTodayList.adapter = adapter
        adapter.submitList(medias)

        binding.featuredTodayShimmer.visibility = View.GONE
        binding.featuredTodayShimmer.stopShimmer()
        binding.featuredTodayParent.visibility = View.VISIBLE
    }

    private fun showImdbOriginals(medias: List<Home.Media>) {
        val adapter = HomeMediaAdapter {

        }

        binding.imdbOriginalsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.imdbOriginalsList.adapter = adapter
        adapter.submitList(medias)

        binding.imdbOriginalsShimmer.visibility = View.GONE
        binding.imdbOriginalsShimmer.stopShimmer()
        binding.imdbOriginalsParent.visibility = View.VISIBLE
    }

    private fun showEditorPicks(medias: List<Home.Media>) {
        val adapter = HomeMediaAdapter {

        }

        binding.editorPicksList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.editorPicksList.adapter = adapter
        adapter.submitList(medias)

        binding.editorPicksShimmer.visibility = View.GONE
        binding.editorPicksShimmer.stopShimmer()
        binding.editorPicksParent.visibility = View.VISIBLE
    }

    private fun showBoxOffice(boxOffice: BoxOffice) {
        try {
            val sourceFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val startDate = sourceFormat.parse(boxOffice.startDate)
            val endDate = sourceFormat.parse(boxOffice.endDate)
            val targetFormat = SimpleDateFormat("MMMM-dd", Locale.US)

            binding.boxOfficeTitleWidget.setSubtitle(
                String.format(
                    resources.getString(
                        R.string.top_box_office_subtitle,
                        targetFormat.format(startDate) + ", " + targetFormat.format(endDate)
                    )
                )
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }


        val adapter = HomeBoxOfficeAdapter {

        }

        binding.boxOfficeList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.boxOfficeList.adapter = adapter
        adapter.submitList(boxOffice.boxOfficeItems)

        binding.boxOfficeParent.visibility = View.VISIBLE
    }

    private fun showHomeLoading() {
        binding.trailersParent.visibility = View.GONE
        binding.trailersShimmer.visibility = View.VISIBLE
        binding.trailersShimmer.startShimmer()

        binding.featuredTodayParent.visibility = View.GONE
        binding.featuredTodayShimmer.visibility = View.VISIBLE
        binding.featuredTodayShimmer.startShimmer()

        binding.imdbOriginalsParent.visibility = View.GONE
        binding.imdbOriginalsShimmer.visibility = View.VISIBLE
        binding.imdbOriginalsShimmer.startShimmer()

        binding.editorPicksParent.visibility = View.GONE
        binding.editorPicksShimmer.visibility = View.VISIBLE
        binding.editorPicksShimmer.startShimmer()

        binding.newsParent.visibility = View.GONE
        binding.newsShimmer.visibility = View.VISIBLE
        binding.newsShimmer.startShimmer()
    }

    private fun showHomeExtraLoading() {

        binding.fanFavoritesParent.visibility = View.GONE
        binding.fanFavoritesShimmer.visibility = View.VISIBLE
        binding.fanFavoritesShimmer.startShimmer()

        binding.streamProvidersParent.visibility = View.GONE
        binding.streamProvidersShimmer.visibility = View.VISIBLE
        binding.streamProvidersShimmer.startShimmer()

        binding.inTheatersParent.visibility = View.GONE
        binding.inTheatersShimmer.visibility = View.VISIBLE
        binding.inTheatersShimmer.startShimmer()

        binding.comingSoonParent.visibility = View.GONE
        binding.comingSoonShimmer.visibility = View.VISIBLE
        binding.comingSoonShimmer.startShimmer()

        binding.bornTodayParent.visibility = View.GONE
        binding.bornTodayShimmer.visibility = View.VISIBLE
        binding.bornTodayShimmer.startShimmer()
    }
}