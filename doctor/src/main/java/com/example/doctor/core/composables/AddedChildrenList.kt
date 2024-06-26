package com.example.doctor.core.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.doctor.core.enums.Month
import com.example.doctor.data.model.child.Child
import com.example.doctor.data.model.child.VaccineTableItem
import com.example.doctor.data.model.date.Age
import com.example.doctor.data.model.date.FullDate
import com.example.doctor.data.model.vaccine.Vaccine
import com.example.doctor.ui.theme.MediCareTheme
import com.example.doctor.ui.theme.Spacing

@Composable
fun AddedChildrenList(
    onChildCardClick:(Child)->Unit,
    modifier: Modifier=Modifier,
    children:List<Child> = emptyList(),
) {
    LazyColumn(modifier=modifier) {
        items(children){child->
            ChildCardComponent(
                child=child,
                onChildCardClick=onChildCardClick
            )
            Spacer(modifier = Modifier.height(Spacing.small))
        }
    }
}


@Preview
@Composable
private fun VaccinationTableListItemPreview() {
    MediCareTheme {
        Surface {
            VaccinationTableListItem(
                VaccineTableItem(
                    id = "",
                    vaccine = Vaccine(
                        name = "Vaccine name",
                        description = "",
                        fromAge = Age(),
                        toAge = Age(),
                        availabilityStartDate = FullDate(),
                        lastAvailableDate = FullDate(),
                        conflicts = emptyList(),
                        visitNumber = 1
                    ),
                    vaccineDate = FullDate(day = 1, month = Month.JUN, year = 2024),
                )
            )
        }
    }
}

@Preview
@Composable
private fun VaccinationTableListPreview() {
    MediCareTheme {
        Surface {
            VaccinationTableList(emptyList())
        }
    }
}