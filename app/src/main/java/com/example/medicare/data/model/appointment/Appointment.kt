package com.example.medicare.data.model.appointment

import com.example.medicare.data.model.clinic.Clinic
import com.example.medicare.data.model.date.FullDate
import com.example.medicare.data.model.date.TimeSocket
import com.google.firebase.firestore.DocumentId

data class Appointment(
    @DocumentId
    val id: String = "",
    val clinicId :String= "",
    val userId : String="",
    val patientName : String="",
    val date: FullDate= FullDate(),
    val timeSocket: TimeSocket= TimeSocket(),
    val vaccineId : String="",
    val clinic : Clinic= Clinic()
 ){
    private constructor() : this(
        "",
        "",
        "",
        "",
        FullDate(),
        TimeSocket(),
        vaccineId = "",
        Clinic()
    )
}


