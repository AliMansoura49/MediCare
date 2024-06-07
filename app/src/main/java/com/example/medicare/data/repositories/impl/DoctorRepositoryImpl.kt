package com.example.medicare.data.repositories.impl

import com.example.medicare.core.constants.DatabaseCollections
import com.example.medicare.data.model.user.Doctor
import com.example.medicare.data.repositories.DoctorRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor(
    database: FirebaseFirestore
): DoctorRepository {
    private val doctorRef = database.collection(DatabaseCollections.DOCTORS_COLLECTION)

    override suspend fun addDoctor(doctor: Doctor) {
        doctorRef.add(doctor).await()
    }
}