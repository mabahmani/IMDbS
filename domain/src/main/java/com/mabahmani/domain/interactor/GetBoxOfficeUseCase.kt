package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.ChartRepository
import javax.inject.Inject

class GetBoxOfficeUseCase @Inject constructor(private val chartRepository: ChartRepository){
    suspend operator fun invoke() = chartRepository.getBoxOffice()
}