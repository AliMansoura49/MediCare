package com.example.medicare.data.model.vaccine

import com.example.medicare.data.model.date.Age
import com.example.medicare.data.model.date.FullDate
import com.example.medicare.core.enums.VaccineState
import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.Serializable

data class Vaccine(
    @DocumentId
    val id : String ="",
    val name : String ="",
    val description : String = "",
    val fromAge : Age? = null,
    val toAge: Age? = null,
    val availabilityStartDate : FullDate = FullDate(),
    val lastAvailableDate : FullDate = FullDate(),
    val conflicts : List<String> = emptyList(),
    val visitNumber : Int = 0,
)
{
    private constructor() : this(
        id = "",
        name = "",
        description = "",
        fromAge = Age(),
        toAge = Age(),
        availabilityStartDate = FullDate(),
        lastAvailableDate = FullDate(),
        conflicts = listOf(),
        visitNumber = 0
    )
    fun getVaccineState() : VaccineState {
        val comparison = FullDate.compareFullDates(lastAvailableDate , FullDate.getCurrentDate())
        return if(comparison >= 0 ){
            VaccineState.AVAILABLE
        }else{
            VaccineState.NOT_AVAILABLE
        }
    }
}