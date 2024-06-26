package com.example.medicare.data.model.date

import com.example.medicare.core.enums.TimeSocketState
import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.Serializable

data class TimeSocket (
    val time: Time,
    val state : TimeSocketState
){
    constructor() : this(time = Time(),state = TimeSocketState.FREE)
}