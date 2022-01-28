package com.mabahmani.data.ps

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mabahmani.data.ds.RemoteDataSource
import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.data.vo.res.SearchNameRes
import com.mabahmani.domain.vo.common.Name
import com.mabahmani.domain.vo.common.Title
import java.lang.RuntimeException
import javax.inject.Inject

class SearchTitlesPagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val certificates: String?,
    private val colors: String?,
    private val companies: String?,
    private val countries: String?,
    private val genres: String?,
    private val groups: String?,
    private val keywords: String?,
    private val languages: String?,
    private val locations: String?,
    private val plot: String?,
    private val releaseDate: String?,
    private val role: String?,
    private val runtime: String?,
    private val sort: String?,
    private val title: String?,
    private val titleType: String?,
    private val userRating: String?
) : PagingSource<Int, Title>() {

    private val pageSize = 50

    override fun getRefreshKey(state: PagingState<Int, Title>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Title> {
        return try {

            val nextPageNumber = params.key ?: 1

            val response = remoteDataSource.searchTitles(
                certificates,
                colors,
                companies,
                countries,
                genres,
                groups,
                keywords,
                languages,
                locations,
                plot,
                releaseDate,
                role,
                runtime,
                sort,
                nextPageNumber.toString(),
                title,
                titleType,
                userRating
            )

            if (response.isSuccess) {
                LoadResult.Page(
                    data = response.getOrDefault(ApiResponse(mutableListOf(), true, "")).data.map { it.toTitle() },
                    prevKey = null,
                    nextKey = nextPageNumber + pageSize
                )

            } else {
                LoadResult.Error(response.exceptionOrNull() ?: RuntimeException())
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}