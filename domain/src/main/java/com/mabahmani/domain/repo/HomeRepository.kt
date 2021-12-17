package com.mabahmani.domain.repo

import com.mabahmani.domain.vo.Home
import com.mabahmani.domain.vo.HomeExtra

interface HomeRepository {

    suspend fun getHome() : Result<Home>

    suspend fun getHomeExtra() : Result<HomeExtra>

}