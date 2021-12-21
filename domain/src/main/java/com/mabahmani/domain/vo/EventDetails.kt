package com.mabahmani.domain.vo

import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.domain.vo.common.Id
import com.mabahmani.domain.vo.common.Image

data class EventDetails(
    val eventName: String,
    val eventCaption: String,
    val eventYear: String,
    val awards:List<Award>
){
    data class Award(
        val awardName: String,
        val awardCategories: List<AwardCategory>
    ){
        data class AwardCategory(
            val awardCategoryTitle: String,
            val nominees: List<Nominee>
        ){
            data class Nominee(
                val winner: Boolean,
                val image: Image,
                val note: String,
                val id: String,
                val name: String,
                val primaryNominees: List<SubNominee>,
                val secondaryNominees: List<SubNominee>,
            ){
                fun evalId():Id{
                    return if (id.startsWith("tt"))
                        Id.TitleId
                    else
                        Id.NameId
                }

                data class SubNominee(
                    val image: Image,
                    val note: String,
                    val id: String,
                    val name: String,
                ){
                    fun evalId():Id{
                        return if (id.startsWith("tt"))
                            Id.TitleId
                        else
                            Id.NameId
                    }
                }
            }
        }
    }
}
