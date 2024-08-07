
package com.example.medicare.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dispensary.ui.composables.VaccinationAppointmentCardComponent
import com.example.medicare.data.model.appointment.Appointment
import com.example.medicare.ui.theme.MediCareTheme
import com.example.medicare.ui.theme.Spacing

@Composable
fun UpcomingVaccinationAppointmentHorizontalList(
    upcomingVaccinationAppointments: List<Appointment> = emptyList(),
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = Spacing.medium)
    ) {
        items(upcomingVaccinationAppointments) { upcomingVaccinationAppointment ->
            VaccinationAppointmentCardComponent(
                onClick = { /*TODO*/ },
                vaccinationAppointment=upcomingVaccinationAppointment,
                modifier = Modifier.width(300.dp)
            )
            Spacer(modifier = Modifier.width(Spacing.medium))
        }
    }
}

@Composable
fun UpcomingVaccinationAppointmentVerticalList(
    upcomingVaccinationAppointments: List<Appointment> = emptyList(),
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = Spacing.medium)
    ) {
        items(upcomingVaccinationAppointments) { upcomingVaccinationAppointment ->
            VaccinationAppointmentCardComponent(
                onClick = { /*TODO*/ },
                vaccinationAppointment = upcomingVaccinationAppointment,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(Spacing.extraSmall))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VaccinationAppointmentHorizontalListPreview() {
    MediCareTheme {
        Surface {
            UpcomingVaccinationAppointmentHorizontalList(emptyList())
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VaccinationAppointmentVerticalListPreview() {
    MediCareTheme {
        Surface {
            UpcomingVaccinationAppointmentVerticalList()
        }
    }
}