package com.example.doctor.presentation.schedule

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.doctor.R
import com.example.doctor.core.composables.ClinicAppointmentVerticalList
import com.example.doctor.core.composables.DatePickerButtonComponent
import com.example.doctor.core.composables.DaySocketHorizontalList
import com.example.doctor.core.composables.MedicareTopAppBar
import com.example.doctor.core.composables.SpannableTextComponent
import com.example.doctor.core.toFullDate
import com.example.doctor.data.fake.listOfAppointments
import com.example.doctor.data.model.appointment.Appointment
import com.example.doctor.ui.theme.MediCareTheme
import com.example.doctor.ui.theme.Spacing
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate

@Composable
fun ScheduleScreen(
    modifier:Modifier=Modifier,
    uiState: ScheduleUiState,
    clinicsAppointments:List<Appointment>,
    updateBookedDateEvent:(LocalDate)->Unit,
) {

    MyDatePickerDialog(
        onConfirmButtonClick = { date ->
            updateBookedDateEvent(date)
            uiState.datePickerState.hide()
        },
        datePickerState = uiState.datePickerState
    )

    Scaffold(
        topBar = {
            MedicareTopAppBar(
                title = R.string.app_name,
            )
        }
    ) { contentPadding ->
        Surface(modifier = Modifier.padding(contentPadding)) {

            Column(
                modifier = modifier
                    .fillMaxSize(),
            ) {
                HomeScreenSection(title = R.string.upcoming_appointments) {
                    Column {
                        Spacer(modifier = Modifier.height(Spacing.small))
                        DatePickerButtonComponent(
                            date =uiState.bookedDate.toFullDate(),
                            onClick = {
                                uiState.datePickerState.show()
                            },
                            modifier = Modifier.padding(horizontal = Spacing.medium),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(Spacing.medium))

                DaySocketHorizontalList(
                    workDays = uiState.clinic.workDays,
                    selectedIndex = uiState.selectedDaySocketIndex,
                    updateSelectedIndex = updateBookedDateEvent
                )

                Spacer(modifier = Modifier.height(Spacing.medium))

                ClinicAppointmentVerticalList(
                    clinicsAppointments=clinicsAppointments
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onConfirmButtonClick: (LocalDate) -> Unit,
    datePickerState: UseCaseState
) {
    val currentDate = LocalDate.now()

    CalendarDialog(
        state = datePickerState,
        config = CalendarConfig(
            boundary =LocalDate.now()..currentDate.plusDays(30)
        ),
        selection = CalendarSelection.Date { date ->
            onConfirmButtonClick(date)
        }
    )
}

@Composable
fun HomeScreenSection(
    @StringRes title: Int,
    showSubtext: Boolean = false,
    onSubtextClick: () -> Unit = {},
    numberOfUpcomingAppointments: Int = 0,
    showSeeAllButton: Boolean = false,
    modifier: Modifier = Modifier,
    list: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = Spacing.medium,
                    end = Spacing.medium
                )
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = Spacing.medium,
                        end = Spacing.medium,
                    ),
            ) {
                Text(
                    text = stringResource(id = title),
                    style = MaterialTheme.typography.titleMedium
                )
                if (showSubtext)
                    Text(
                        text = "$numberOfUpcomingAppointments upcoming",
                        style = MaterialTheme.typography.bodySmall,
                    )
            }
            if (showSeeAllButton)
                SpannableTextComponent(
                    onCLick = onSubtextClick,
                    text1 = stringResource(id = R.string.blank),
                    text2 = stringResource(id = R.string.see_all),
                    spanStyle = SpanStyle(color = MaterialTheme.colorScheme.primary),
                    textStyle = MaterialTheme.typography.labelLarge
                )
        }
        list()
    }
}

@Preview
@Composable
private fun ScheduleScreenPreview() {
    MediCareTheme {
        Surface {
            ScheduleScreen(
                uiState = ScheduleUiState(),
                clinicsAppointments = listOfAppointments,
                updateBookedDateEvent = {}
            )
        }
    }
}