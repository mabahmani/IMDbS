package com.mabahmani.data.ps

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mabahmani.data.ds.RemoteDataSource
import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.data.vo.res.VideosRes
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.domain.vo.common.Video

class NameVideosPagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val nameId: NameId,
) : PagingSource<Int, Video>() {

    override fun getRefreshKey(state: PagingState<Int, Video>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Video> {
        return try {

            val nextPageNumber = params.key ?: 1

            val response = remoteDataSource.getNameVideos(
                nameId.value,
                nextPageNumber.toString(),
            )


            if (response.isSuccess) {
                LoadResult.Page(
                    data = response.getOrDefault(ApiResponse(VideosRes("","", listOf()), true, "")).data.toVideos(),
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