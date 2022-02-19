package com.mabahmani.data.ps

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mabahmani.data.ds.RemoteDataSource
import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.data.vo.res.ImagesRes
import com.mabahmani.domain.vo.common.*

class ListImagesPagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val listId: ListId,
) : PagingSource<Int, ImageLink>() {

    override fun getRefreshKey(state: PagingState<Int, ImageLink>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageLink> {
        return try {

            val nextPageNumber = params.key ?: 1

            val response = remoteDataSource.getListImages(
                listId.value
            )


            if (response.isSuccess) {
                LoadResult.Page(
                    data = response.getOrDefault(ApiResponse(ImagesRes(listOf(),"",""), true, "")).data.toImages(),
                    prevKey = null,
                    nextKey = null
                )

            } else {
                LoadResult.Error(response.exceptionOrNull() ?: RuntimeException())
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}