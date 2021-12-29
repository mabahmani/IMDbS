package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSource
import com.mabahmani.domain.repo.EventRepository
import com.mabahmani.domain.vo.EventDetails
import com.mabahmani.domain.vo.common.EventId
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor (private val remoteDataSource: RemoteDataSource): EventRepository {
    override suspend fun getEventDetails(eventId: EventId, eventYear: Int): Result<EventDetails> {

        val remoteResult = remoteDataSource.getEventDetails(eventId.value, eventYear.toString())

        return if(remoteResult.isSuccess){
            try {
                Result.success(remoteResult.getOrNull()!!.data.toEventDetails())
            }catch (ex: Exception){
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull()?: java.lang.Exception())
    }
}