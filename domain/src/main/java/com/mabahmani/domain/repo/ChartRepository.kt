package com.mabahmani.domain.repo

import com.mabahmani.domain.vo.common.BoxOffice
import com.mabahmani.domain.vo.common.Title

interface ChartRepository {

    suspend fun getTop250Movies() : Result<List<Title>>

    suspend fun getTop250TvShows() : Result<List<Title>>

    suspend fun getPopularMovies() : Result<List<Title>>

    suspend fun getPopularTvShows() : Result<List<Title>>

    suspend fun getBottom100Movies() : Result<List<Title>>

    suspend fun getBoxOffice() : Result<BoxOffice>
}