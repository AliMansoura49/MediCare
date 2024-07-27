package com.example.doctor.presentation.schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctor.core.getMonthByJavaMonth
import com.example.doctor.core.toFullDate
import com.example.doctor.data.model.appointment.Appointment
import com.example.doctor.data.model.clinic.Clinic
import com.example.doctor.data.model.date.FullDate
import com.example.doctor.data.repositories.AppointmentRepository
import com.example.doctor.data.repositories.ClinicRepository
import com.example.doctor.data.services.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val appointmentRepository: AppointmentRepository,
    private val clinicRepository: ClinicRepository,
    private val accountService: AccountService,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState = _uiState.asStateFlow()

    var appointments=appointmentRepository.appointments
    val appointmentsToNumberOfVisits:MutableList<Int> = mutableListOf()

    init {
        updateClinic()
        getNumberOfVisits()
    }

    fun updateClinic() {
        viewModelScope.launch {
            val clinicId = clinicRepository.getClinicIdByDoctor(accountService.currentUserId)
            Log.v("currentUserId",accountService.currentUserId)
            if(clinicId==null) throw Exception("The clinicId is null")
            val clinic = clinicRepository.getClinicById(clinicId)
            _uiState.update { it.copy(clinic = clinic ?: Clinic()) }
        }
    }

    fun updateBookedDate(date: LocalDate) {
        _uiState.update { it.copy(bookedDate = date) }
        updateSelectedDaySocketIndex(date)
        updateAppointments()
    }

    private fun updateAppointments() {
        ///appointments=appointmentRepository.appointments.at(uiState.value.bookedDate.toFullDate())
    }


    private fun updateSelectedDaySocketIndex(date: LocalDate) {
        val index = uiState.value.clinic.daySockets.indexOfFirst {
            it.date.equals(
                date.toFullDate()
            )
        }
        _uiState.update { it.copy(selectedDaySocketIndex = index) }
    }

    fun getNumberOfVisits() {
        val job = viewModelScope.async {
            appointments=appointmentRepository.getAppointmentsByDate(uiState.value.bookedDate.toFullDate())

            appointments.map { appointments ->
                appointments.forEach { appt->
                    appointmentsToNumberOfVisits.add(
                            appointmentRepository.getNumberOfAppointments(appt.userId)
                            )
                }
            }
        }
        //Log.v("AppointmentsWithVisitNumber",appointmentsToNumberOfVisits.toString())
    }
}