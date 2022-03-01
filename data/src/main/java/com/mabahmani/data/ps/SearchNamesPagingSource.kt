package com.mabahmani.data.ps

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mabahmani.data.ds.RemoteDataSource
import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.domain.vo.common.Name

class SearchNamesPagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val bio: String?,
    private val birthDate: String?,
    private val birthMonthDay: String?,
    private val birthPlace: String?,
    private val deathDate: String?,
    private val deathPlace: String?,
    private val gender: String?,
    private val groups: String?,
    private val name: String?,
    private val roles: String?,
    private val sort: String?,
    private val starSign: String?,
) : PagingSource<Int,Name>() {

    private val pageSize = 50

    override fun getRefreshKey(state: PagingState<Int,Name>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Name> {
        return try {

            val nextPageNumber = params.key ?: 1

            val response = remoteDataSource.searchNames(
                bio,
                birthDate,
                birthMonthDay,
                birthPlace,
                deathDate,
                deathPlace,
                gender,
                groups,
                name,
                roles,
                sort,
                starSign,
                nextPageNumber.toString()
            )

            if (response.isSuccess){
                LoadResult.Page(
                    data = response.getOrDefault(ApiResponse(mutableListOf(), true, "")).data.map { it.toName() },
                    prevKey = null,
                    nextKey = nextPageNumber + pageSize
                )

            }
            else{
                LoadResult.Error(response.exceptionOrNull() ?: RuntimeException())
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}