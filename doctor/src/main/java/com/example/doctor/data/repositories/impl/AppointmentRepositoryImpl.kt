package com.example.doctor.data.repositories.impl

import com.example.doctor.core.constants.DatabaseCollections
import com.example.doctor.data.model.appointment.Appointment
import com.example.doctor.data.repositories.AppointmentRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AppointmentRepositoryImpl @Inject constructor(
    database : FirebaseFirestore,
    auth: FirebaseAuth
) : AppointmentRepository {

    private val currentUserId = auth.currentUser?.uid ?: ""

    private val appointmentsRef = database.collection(DatabaseCollections.APPOINTMENTS_COLLECTION)

    override suspend fun addAppointment(appointment: Appointment) {
        appointmentsRef.add(appointment).await()
    }

    override suspend fun deleteAppointment(id: String) {
        appointmentsRef.document(id).delete().await()
    }

    override val appointments: Flow<List<Appointment>>
        get() = appointmentsRef.whereEqualTo("userId", currentUserId).snapshots().map { snapshot ->
            snapshot.toObjects(Appointment::class.java)
        }

    suspend fun isValidInput(appointment: Appointment):Boolean{
        return 0 == try {
            appointmentsRef
                .whereEqualTo("date.year", appointment.date.year)
                .whereEqualTo("date.month.ordinal", appointment.date.month.ordinal)
                .whereEqualTo("date.day", appointment.date.day)
                .whereEqualTo("clinicId", appointment.clinicId)
                .whereEqualTo("clinicId",appointment.clinicId)
                .whereEqualTo("timeSocket.time.hour", appointment.timeSocket.time.hour)
                .whereEqualTo("timeSocket.time.dayPeriod.ordinal", appointment.timeSocket.time.dayPeriod.ordinal)
                .get()
                .await()
                .size()// Get the size of the query result
        } catch (e: Exception) {
            // Handle exceptions (e.g., network errors, permission issues)
            0 // Return 0 in case of errors
        }
    }

}