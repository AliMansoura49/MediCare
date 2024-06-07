package com.example.medicare.data.model.clinic

import android.net.Uri
import com.example.medicare.data.model.user.Doctor
import com.example.medicare.data.model.date.DaySocket
import com.example.medicare.data.model.date.WorkDay
import com.google.firebase.firestore.DocumentId

data class Clinic(
    @DocumentId
    val id: String ="",
    val name: String = "",
    val imageUrl: Uri? = null,
    val workDays: List<WorkDay> = emptyList(),
    val daySockets: List<DaySocket> = emptyList(),
    val responsibleDoctor: Doctor,
    val services : List<String> = emptyList()
){
    constructor() : this(
        "", "", null, emptyList(), emptyList(), Doctor(
        firstName = "Ali",
        lastName = "Mansoura"
    ), emptyList())
}
