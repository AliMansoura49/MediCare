package com.example.dispensary.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.medicare.R
import com.example.medicare.core.calculateRemainingTime
import com.example.medicare.core.convertToProperCase
import com.example.medicare.core.enums.DayPeriod
import com.example.medicare.core.enums.Month
import com.example.medicare.core.enums.TimeSocketState
import com.example.medicare.core.enums.TimeUnit
import com.example.medicare.core.formatDate
import com.example.medicare.core.formatTime
import com.example.medicare.core.getDayOfWeek
import com.example.medicare.core.isOpenNow
import com.example.medicare.data.model.appointment.Appointment
import com.example.medicare.data.model.child.Child
import com.example.medicare.data.model.clinic.Clinic
import com.example.medicare.data.model.date.FullDate
import com.example.medicare.data.model.date.RemainingTime
import com.example.medicare.data.model.date.Time
import com.example.medicare.data.model.date.TimeSocket
import com.example.medicare.data.model.notification.Notification
import com.example.medicare.data.model.user.Doctor
import com.example.medicare.ui.theme.Spacing
import com.example.medicare.ui.theme.green
import com.example.medicare.ui.theme.primary_container
import com.example.medicare.ui.theme.secondary_container
import com.example.medicare.ui.theme.tertiary
import java.time.Clock
import java.time.DayOfWeek
import java.time.Duration.ofHours
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

@Composable
fun SectionCardComponent(
    modifier: Modifier = Modifier,
    imageUrl: String,
    isSelected: Boolean = false,
    title: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .width(64.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .border(
                    width = if (isSelected) 2.dp else 0.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                ),
            colors = CardDefaults.cardColors(containerColor = primary_container)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier.padding(10.dp),
            )
        }
        Spacer(modifier = Modifier.height(Spacing.extraSmall))
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResponsibleDoctorCardComponent(
    doctor: Doctor,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
                    .height(100.dp)
            ) {
                Text(
                    text = doctor.fullName,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = doctor.speciality,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                ElevatedButtonComponent(
                    text = R.string.book,
                    onClick = { onClick() },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            AsyncImage(
                model = doctor.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaccinationAppointmentCardComponent(
    onClick: () -> Unit,
    vaccinationAppointment: Appointment,
    modifier: Modifier = Modifier,
) {
    val hours =
        if (vaccinationAppointment.timeSocket.time.hour.toString().length == 1)
            "0${vaccinationAppointment.timeSocket.time.hour}"
        else
            "${vaccinationAppointment.timeSocket.time.hour}"

    val minutes =
        if (vaccinationAppointment.timeSocket.time.minute.toString().length == 1)
            "0${vaccinationAppointment.timeSocket.time.minute}"
        else
            "${vaccinationAppointment.timeSocket.time.minute}"


    val remainingTime = vaccinationAppointment.calculateRemainingTime()


    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = secondary_container)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Card(
                colors = CardDefaults.cardColors(containerColor = tertiary)
            ) {
                Column(
                    modifier = Modifier
                        .width(44.dp)
                        .height(56.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = vaccinationAppointment.date.day.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onTertiary,
                    )
                    Text(
                        text = vaccinationAppointment.date.getDayOfWeek().name.convertToProperCase(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onTertiary,
                    )
                }
            }
            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = vaccinationAppointment.patientName,
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text =
                        "$hours:$minutes ${vaccinationAppointment.timeSocket.time.dayPeriod.name}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = if (remainingTime.remainingTime <= 0)
                            ""
                        else
                            "${remainingTime.remainingTime} ${remainingTime.timeUnit.name.lowercase()}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
                Text(
                    text = vaccinationAppointment.vaccineId,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicAppointmentCardComponent(
    onClick: () -> Unit,
    clinicAppointment: Appointment,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = secondary_container)
    ) {
        val hours =
            if (clinicAppointment.timeSocket.time.hour.toString().length == 1)
                "0${clinicAppointment.timeSocket.time.hour}"
            else
                "${clinicAppointment.timeSocket.time.hour}"

        val minutes =
            if (clinicAppointment.timeSocket.time.minute.toString().length == 1)
                "0${clinicAppointment.timeSocket.time.minute}"
            else
                "${clinicAppointment.timeSocket.time.minute}"

        val remainingTime = clinicAppointment.calculateRemainingTime()

        Row(modifier = Modifier.padding(Spacing.small)) {
            Card(
                colors = CardDefaults.cardColors(containerColor = tertiary)
            ) {
                Column(
                    modifier = Modifier
                        .width(44.dp)
                        .height(56.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = clinicAppointment.date.day.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        text = clinicAppointment.date.getDayOfWeek().name.convertToProperCase(),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = clinicAppointment.patientName,
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "$hours:$minutes ${clinicAppointment.timeSocket.time.dayPeriod.name}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = if (remainingTime.remainingTime <= 0)
                            ""
                        else
                            "${remainingTime.remainingTime} ${remainingTime.timeUnit.name.lowercase()}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = clinicAppointment.clinic.responsibleDoctor.fullName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = clinicAppointment.clinic.responsibleDoctor.speciality,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChildCardComponent(
    onChildCardClick: (String) -> Unit,
    child: Child,
    modifier: Modifier = Modifier,
) {
    val day =
        if (child.birthDate.day.toString().length == 1) "0${child.birthDate.day}" else child.birthDate.day
    val monthNumber =
        if ((child.birthDate.month.ordinal + 1).toString().length == 1) "0${child.birthDate.month.ordinal + 1}" else "${child.birthDate.month.ordinal + 1}"

    Card(
        modifier = modifier
            .fillMaxWidth(),
        onClick = {
            onChildCardClick(child.id)
        },
        colors = CardDefaults.cardColors(containerColor = secondary_container)
    ) {
        Row(modifier = Modifier.padding(Spacing.small)) {
            Card(
                colors = CardDefaults.cardColors(containerColor = tertiary)
            ) {
                Column(
                    modifier = Modifier
                        .width(44.dp)
                        .height(56.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = child.childNumber.firstNumber.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onTertiary,
                    )
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .width(24.dp)
                            .background(color = MaterialTheme.colorScheme.onTertiary)
                    )
                    Text(
                        text = child.childNumber.secondNumber.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onTertiary,
                    )
                }
            }
            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${child.firstName} ${child.lastName}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = child.father,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.outline,
                    )
                    Text(
                        text = "$day/$monthNumber/${child.birthDate.year}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
                Text(
                    text = child.mother,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvailableVaccinationNotificationCardComponent(
    onClick: () -> Unit,
    notification: Notification,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = secondary_container)
    ) {
        Row(modifier = Modifier.padding(Spacing.small)) {
            Image(
                painter = painterResource(id = R.drawable.inoculate),
                contentDescription = null,
                modifier = Modifier
                    .width(44.dp)
                    .height(56.dp),
            )
            Column(
                modifier = Modifier.padding(start = Spacing.medium),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.vaccination_available),
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "${notification.vaccine?.availabilityStartDate?.formatDate()} - ${notification.vaccine?.lastAvailableDate?.formatDate()}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.outline,
                    )
                    Text(
                        text = notification.vaccine?.getVaccineState().toString().convertToProperCase(),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                Text(
                    text = notification.vaccine?.name ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentReminderNotificationCardComponent(
    onClick: () -> Unit,
    notification: Notification,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = secondary_container)
    ) {
        Row(modifier = Modifier.padding(Spacing.small)) {
            Image(
                painter = painterResource(id = R.drawable.appointment),
                contentDescription = null,
                modifier = Modifier
                    .width(44.dp)
                    .height(56.dp),
            )
            Column(
                modifier = Modifier.padding(start = Spacing.medium),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = notification.patientName,
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "notification.appointment",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.outline,
                    )
                    Text(
                        text = "notification.appointment",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = notification.doctorName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "notification.doctor.speciality",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
            }
        }
    }
}

@Composable
fun DateCardComponent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    date: FullDate,
    isSelected: Boolean,
    isDisabled: Boolean,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor =
            if (isDisabled)
                MaterialTheme.colorScheme.outlineVariant
            else if (!isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.primary,
        ),
        onClick = {
            if (!isDisabled)
                onClick()
        },
    ) {
        Column(
            modifier = Modifier
                .width(44.dp)
                .height(56.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${date.day}",
                style = MaterialTheme.typography.bodyLarge,
                color =
                if (isDisabled)
                    MaterialTheme.colorScheme.inverseOnSurface
                else if (!isSelected)
                    MaterialTheme.colorScheme.onPrimaryContainer
                else
                    MaterialTheme.colorScheme.onSecondary,
            )
            Text(
                text = date.getDayOfWeek().name.convertToProperCase(),
                style = MaterialTheme.typography.bodyLarge,
                color =
                if (isDisabled)
                    MaterialTheme.colorScheme.inverseOnSurface
                else if (!isSelected)
                    MaterialTheme.colorScheme.onPrimaryContainer
                else
                    MaterialTheme.colorScheme.onSecondary,
            )
        }
    }
}

@Composable
fun TimeSocketCardComponent(
    timeSocket: TimeSocket,
    onClick: (Int) -> Unit,
    currentIndex: Int,
    color: Color = MaterialTheme.colorScheme.background,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clickable {
                onClick(currentIndex)
            }
            .background(
                color = color,
                shape = MaterialTheme.shapes.small
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = MaterialTheme.shapes.small
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = formateTime(timeSocket),
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 16.dp)
        )
    }
}

fun formateTime(timeSocket: TimeSocket): String {
    return if (timeSocket.time.hour.toString().length == 2 && timeSocket.time.minute.toString().length == 2)
        "${timeSocket.time.hour}:${timeSocket.time.minute} ${timeSocket.time.dayPeriod}"
    else if (timeSocket.time.hour.toString().length == 1 && timeSocket.time.minute.toString().length == 2)
        "0${timeSocket.time.hour}:${timeSocket.time.minute} ${timeSocket.time.dayPeriod}"
    else if (timeSocket.time.hour.toString().length == 2 && timeSocket.time.minute.toString().length == 1)
        "${timeSocket.time.hour}:0${timeSocket.time.minute} ${timeSocket.time.dayPeriod}"
    else
        "0${timeSocket.time.hour}:0${timeSocket.time.minute} ${timeSocket.time.dayPeriod}"
}


@Composable
fun ClinicInformationCardComponent(
    clinic: Clinic,
    modifier: Modifier = Modifier,
) {

    Card(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp)) {
            Box(contentAlignment = Alignment.BottomEnd) {
                AsyncImage(
                    model = clinic.responsibleDoctor.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .height(120.dp)
                        .width(110.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
            }
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = "${clinic.responsibleDoctor.firstName} ${clinic.responsibleDoctor.lastName}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = clinic.responsibleDoctor.speciality,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (clinic.workDays.isNotEmpty()) {
                    Text(
                        text = "${clinic.workDays[0].openingTime.formatTime()} - ${clinic.workDays[0].closingTime.formatTime()}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Text(
                    text = if (clinic.isOpenNow()) stringResource(id = R.string.open_now)
                    else stringResource(id = R.string.closed_now),
                    color = if (clinic.isOpenNow()) green
                    else Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                )
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier.size(36.dp),
                        colors = CardDefaults.cardColors(containerColor = primary_container)
                    ) {
                        AsyncImage(
                            model = clinic.imageUrl,
                            contentDescription = null,
                            modifier = Modifier.padding(4.dp),
                        )
                    }
                    Text(
                        text = clinic.name,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SectionCardComponentPreview() {
    MaterialTheme {
        Surface {
            SectionCardComponent(
                modifier = Modifier,
                imageUrl = "",
                title = stringResource(id = R.string.test_vaccines),
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResponsibleDoctorCardComponentPreview() {
    MaterialTheme {
        Surface {
            ResponsibleDoctorCardComponent(
                modifier = Modifier,
                onClick = {},
                doctor = Doctor()
            )
        }
    }
}

@Preview
@Composable
private fun VaccinationCardAppointmentComponentPreview() {
    MaterialTheme {
        Surface {
            VaccinationAppointmentCardComponent(
                modifier = Modifier,
                onClick = {},
                vaccinationAppointment = Appointment()
            )
        }
    }
}

@Preview
@Composable
private fun ClinicCardAppointmentComponentPreview() {
    MaterialTheme {
        Surface {
            ClinicAppointmentCardComponent(
                modifier = Modifier,
                onClick = {},
                clinicAppointment = Appointment()
            )
        }
    }
}

@Preview
@Composable
private fun ChildCardComponentPreview() {
    MaterialTheme {
        Surface {
            ChildCardComponent(
                modifier = Modifier,
                onChildCardClick = {},
                child = Child(),
            )
        }
    }
}

@Preview
@Composable
private fun NotificationCardComponentPreview() {
    MaterialTheme {
        Surface {
            AvailableVaccinationNotificationCardComponent(
                modifier = Modifier,
                onClick = {},
                notification = Notification()
            )
        }
    }
}

@Preview
@Composable
private fun AppointmentReminderNotificationCardComponentPreview() {
    MaterialTheme {
        Surface {
            AppointmentReminderNotificationCardComponent(
                modifier = Modifier,
                onClick = {},
                notification = Notification()
            )
        }
    }
}

@Preview
@Composable
private fun DateCardComponentPreview() {
    MaterialTheme {
        Surface {
            Row {
                DateCardComponent(
                    modifier = Modifier,
                    onClick = {},
                    date = FullDate(10, Month.AUG, 2024),
                    isSelected = false,
                    isDisabled = false
                )
                DateCardComponent(
                    modifier = Modifier,
                    onClick = {},
                    date = FullDate(10, Month.AUG, 2024),
                    isSelected = true,
                    isDisabled = false
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeCardComponentPreview() {
    MaterialTheme {
        Surface {
            TimeSocketCardComponent(
                timeSocket = TimeSocket(
                    time = Time(9, 0, DayPeriod.AM),
                    state = TimeSocketState.FREE
                ),
                onClick = {},
                currentIndex = 0
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SectionInformationCardComponentPreview() {
    MaterialTheme {
        Surface {
            ClinicInformationCardComponent(
                clinic = Clinic()
            )
        }
    }
}

