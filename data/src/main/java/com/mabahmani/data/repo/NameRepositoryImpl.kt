package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSource
import com.mabahmani.domain.repo.NameRepository
import com.mabahmani.domain.vo.NameAwards
import com.mabahmani.domain.vo.NameBio
import com.mabahmani.domain.vo.NameDetails
import com.mabahmani.domain.vo.common.NameId
import javax.inject.Inject

class NameRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource): NameRepository {
    override suspend fun getNameDetails(nameId: NameId): Result<NameDetails> {
        val remoteResult = remoteDataSource.getNameDetails(nameId.value)

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toNameDetails())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

    override suspend fun getNameAwards(nameId: NameId): Result<NameAwards> {
        val remoteResult = remoteDataSource.getNameAwards(nameId.value)

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toNameAwards())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())    }

    override suspend fun getNameBio(nameId: NameId): Result<NameBio> {
        val remoteResult = remoteDataSource.getNameBio(nameId.value)

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toNameBio())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }
}