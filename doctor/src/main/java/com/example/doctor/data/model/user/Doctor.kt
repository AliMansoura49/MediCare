package com.example.doctor.data.model.user

import com.example.doctor.core.enums.Gender

data class Doctor(
    val id : String ="",
    val firstName : String="",
    val lastName : String="",
    val speciality : String = "",
    val imageUrl : String? = "",
    val gender: Gender = Gender.MALE
){
    val fullName : String = "$firstName $lastName"
    private constructor() : this(
        id = "",
        firstName = "",
        lastName = "",
        speciality = "",
        imageUrl = null,
        gender = Gender.MALE
    )
}