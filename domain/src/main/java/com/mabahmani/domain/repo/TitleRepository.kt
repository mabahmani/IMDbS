package com.mabahmani.domain.repo

import com.mabahmani.domain.vo.*
import com.mabahmani.domain.vo.common.TitleId

interface TitleRepository {

    suspend fun getTitleDetails(titleId: TitleId) : Result<TitleDetails>

    suspend fun getTitleAwards(titleId: TitleId) : Result<TitleAwards>

    suspend fun getTitleFullCasts(titleId: TitleId) : Result<TitleFullCasts>

    suspend fun getTitleParentsGuide(titleId: TitleId) : Result<TitleParentsGuide>

    suspend fun getTitleTechnicalSpecs(titleId: TitleId) : Result<TitleTechnicalSpecs>

}