package com.mabahmani.domain.repo

import com.mabahmani.domain.vo.NameAwards
import com.mabahmani.domain.vo.NameBio
import com.mabahmani.domain.vo.NameDetails
import com.mabahmani.domain.vo.common.NameId

interface NameRepository {

    suspend fun getNameDetails(nameId: NameId) : Result<NameDetails>

    suspend fun getNameAwards(nameId: NameId) : Result<NameAwards>

    suspend fun getNameBio(nameId: NameId) : Result<NameBio>
}