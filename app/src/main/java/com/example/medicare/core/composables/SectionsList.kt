package com.example.medicare.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dispensary.ui.composables.SectionCardComponent
import com.example.medicare.data.model.clinic.Clinic
import com.example.medicare.ui.theme.MediCareTheme
import com.example.medicare.ui.theme.Spacing


@Composable
fun SectionsList(
    modifier:Modifier= Modifier,
    clinics: List<Clinic>,
    onClick:(Int)->Unit,
    currentIndex:Int
) {
    LazyRow(
        modifier=modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = Spacing.medium)
        ) {
        items(clinics.size){ index->
            SectionCardComponent(
                imageUrl = clinics[index].imageUrl?:"",
                title = clinics[index].name,
                onClick ={
                    onClick(index)
                },
                isSelected = currentIndex==index
            )
            Spacer(modifier = Modifier.width(Spacing.medium))
        }
    }
}

@Preview
@Composable
private fun SectionsListPreview() {
    MediCareTheme {
        Surface {
            SectionsList(onClick = {}, clinics = emptyList(), currentIndex = 0)
        }
    }
}