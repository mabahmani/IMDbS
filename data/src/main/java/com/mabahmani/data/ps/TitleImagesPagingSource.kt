package com.mabahmani.data.ps

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mabahmani.data.ds.RemoteDataSource
import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.data.vo.res.ImagesRes
import com.mabahmani.domain.vo.common.ImageLink
import com.mabahmani.domain.vo.common.Title
import com.mabahmani.domain.vo.common.TitleId

class TitleImagesPagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val titleId: TitleId,
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

            val response = remoteDataSource.getTitleImages(
                titleId.value,
                nextPageNumber.toString(),
            )


            if (response.isSuccess) {
                LoadResult.Page(
                    data = response.getOrDefault(ApiResponse(ImagesRes(listOf(),"",""), true, "")).data.toImages(),
                    prevKey = null,
                    nextKey = nextPageNumber + 1
                )

            } else {
                LoadResult.Error(response.exceptionOrNull() ?: RuntimeException())
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}