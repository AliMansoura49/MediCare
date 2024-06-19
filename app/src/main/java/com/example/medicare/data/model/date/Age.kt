package com.example.medicare.data.model.date

import com.example.medicare.core.enums.AgeUnit
import kotlinx.serialization.Serializable

@Serializable
data class Age (
    val age: Int,
    val unit : AgeUnit
){
    constructor() : this(0, AgeUnit.YEARS)
    val fullAge: String = "$age $unit"
}