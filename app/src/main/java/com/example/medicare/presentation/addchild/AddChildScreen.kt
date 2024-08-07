package com.example.medicare.presentation.addchild

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.dispensary.ui.composables.CheckBoxComponent
import com.example.dispensary.ui.composables.ChooseTab
import com.example.dispensary.ui.composables.ChooseTabState
import com.example.dispensary.ui.composables.DatePickerTextField
import com.example.dispensary.ui.composables.ElevatedButtonComponent
import com.example.dispensary.ui.composables.OutlinedTextFieldComponent
import com.example.dispensary.ui.composables.OutlinedTextFieldWithTwoFieldsComponent
import com.example.medicare.R
import com.example.medicare.core.composables.ErrorDialog
import com.example.medicare.core.composables.LoadingDialog
import com.example.medicare.core.composables.MedicareTopAppBar
import com.example.medicare.core.composables.MyDatePickerDialog
import com.example.medicare.core.navigate
import com.example.medicare.presentation.navigation.Destination
import com.example.medicare.ui.theme.MediCareTheme
import com.example.medicare.ui.theme.Spacing
import java.time.LocalDate

@Composable
fun AddChildScreen(
    modifier: Modifier = Modifier,
    navigateToChildrenScreen: () -> Unit,
    onNavigateUpClick: () -> Unit,
    uiState: AddChildUiState,
    updateErrorDialogVisibilityState: (Boolean) -> Unit,
    updateChildNumberEvent: (String) -> Unit,
    updateChildFirstNameEvent: (String) -> Unit,
    updateChildSecondNameEvent: (String) -> Unit,
    updateFatherFirstNameEvent: (String) -> Unit,
    updateFatherSecondNameEvent: (String) -> Unit,
    updateMotherFirstNameEvent: (String) -> Unit,
    updateMotherSecondNameEvent: (String) -> Unit,
    updateGenderEvent: (ChooseTabState) -> Unit,
    updateCheckStateEvent: (Boolean) -> Unit,
    onAddChildClick: () -> Unit,
    clearTextFieldValue:()->Unit,
    updateBookedDateEvent:(LocalDate)->Unit,
) {

    MyDatePickerDialog(
        onConfirmButtonClick = { date ->
            updateBookedDateEvent(date)
            uiState.datePickerState.hide()
        },
        datePickerState = uiState.datePickerState,
        range = LocalDate.MIN..LocalDate.MAX
    )

    ErrorDialog(
        showDialog = uiState.showErrorDialog,
        onDismissRequest = {
            updateErrorDialogVisibilityState(false)
        },
        onConfirmClick = {
            updateErrorDialogVisibilityState(false)
        }
    )
    LoadingDialog(
        showDialog = uiState.showLoadingDialog,
    )

    Log.v("AddChild",uiState.isAddChildSuccessful.toString())
    if (uiState.isAddChildSuccessful)
        navigateToChildrenScreen()

    Scaffold(
        topBar = {
            MedicareTopAppBar(
                showNavigateUpIconButton = true,
                showNotificationIconButton = false,
                title = R.string.app_name,
                onNavigateUpClick = onNavigateUpClick
            )
        }
    ) { contentPadding ->
        Surface(modifier = Modifier.padding(contentPadding)) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(Spacing.medium),
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextFieldComponent(
                    title = stringResource(id = R.string.number),
                    textFieldValue = uiState.number,
                    onValueChange = {
                        updateChildNumberEvent(it)
                    },
                    hint = R.string.number_hint,
                    errorMessage = uiState.numberErrorMessage
                )

                Spacer(modifier = Modifier.height(Spacing.medium))

                OutlinedTextFieldWithTwoFieldsComponent(
                    title = stringResource(id = R.string.name),
                    textFieldValue1 = uiState.childFirstName,
                    textFieldValue2 = uiState.childSecondName,
                    onValueChange1 = {
                        updateChildFirstNameEvent(it)
                    },
                    onValueChange2 = {
                        updateChildSecondNameEvent(it)
                    },
                    hint1 = R.string.first_name_hint,
                    hint2 = R.string.second_name_hint,
                    errorMessage1 = uiState.childSecondNameErrorMessage ,
                    errorMessage2 = uiState.childSecondNameErrorMessage ,
                )

                Spacer(modifier = Modifier.height(Spacing.medium))

                OutlinedTextFieldWithTwoFieldsComponent(
                    title = stringResource(id = R.string.father_name),
                    textFieldValue1 = uiState.fatherFirstName,
                    textFieldValue2 = uiState.fatherSecondName,
                    onValueChange1 = {
                        updateFatherFirstNameEvent(it)
                    },
                    onValueChange2 = {
                        updateFatherSecondNameEvent(it)
                    },
                    hint1 = R.string.first_name_hint,
                    hint2 = R.string.second_name_hint,
                    errorMessage1 = uiState.fatherFirstNameErrorMessage,
                    errorMessage2 = uiState.fatherSecondNameErrorMessage,
                )

                Spacer(modifier = Modifier.height(Spacing.medium))

                OutlinedTextFieldWithTwoFieldsComponent(
                    title = stringResource(id = R.string.mother_name),
                    textFieldValue1 = uiState.motherFirstName,
                    textFieldValue2 = uiState.motherSecondName,
                    onValueChange1 = {
                        updateMotherFirstNameEvent(it)
                    },
                    onValueChange2 = {
                        updateMotherSecondNameEvent(it)
                    },
                    hint1 = R.string.first_name_hint,
                    hint2 = R.string.second_name_hint,
                    errorMessage1 = uiState.motherFirstNameErrorMessage ,
                    errorMessage2 = uiState.motherSecondNameErrorMessage,
                )

                Spacer(modifier = Modifier.height(Spacing.medium))

                DatePickerTextField(
                    title = stringResource(id = R.string.date_of_birth),
                    localDate = uiState.dateOfBirth,
                    onValueChange = {},
                    hint = R.string.birthday_hint,
                    imeAction = ImeAction.Done,
                    errorMessage = uiState.dateOfBirthErrorMessage,
                    onOpenCalendarClick = {uiState.datePickerState.show()},
                    clearTextFieldValue =clearTextFieldValue,
                )

                Spacer(modifier = Modifier.height(Spacing.medium))

                ChooseTab(
                    title = stringResource(id = R.string.gender),
                    text1 = R.string.male,
                    text2 = R.string.female,
                    chooseTabState = uiState.gender,
                    onChooseChange = { chooseTabState ->
                        updateGenderEvent(chooseTabState)
                    },
                    errorMessage =
                        uiState.genderErrorMessage
                )

                Spacer(modifier = Modifier.height(Spacing.large))

                CheckBoxComponent(
                    checked = uiState.acceptPrivacyIsChecked,
                    onCheckedChange = {
                        updateCheckStateEvent(it)
                    },
                    text1 = stringResource(id = R.string.checkbox_auth_text1),
                    text2 = stringResource(id = R.string.checkbox_auth_text2),
                    text3 = stringResource(id = R.string.checkbox_auth_text3),
                )

                Spacer(modifier = Modifier.height(Spacing.large))

                ElevatedButtonComponent(
                    text = R.string.add_child,
                    onClick = {
                        onAddChildClick()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }


}

@Preview
@Composable
private fun AddChildScreenPreview() {
    MediCareTheme {
        Surface {
            AddChildScreen(
                onNavigateUpClick = {},
                onAddChildClick = {},
                updateChildSecondNameEvent = {},
                updateFatherSecondNameEvent = {},
                updateMotherSecondNameEvent = {},
                updateErrorDialogVisibilityState = {},
                updateCheckStateEvent = {},
                updateGenderEvent = {},
                updateChildFirstNameEvent = {},
                updateChildNumberEvent = {},
                updateFatherFirstNameEvent = {},
                updateMotherFirstNameEvent = {},
                navigateToChildrenScreen = {},
                uiState = AddChildUiState(),
                clearTextFieldValue={},
                updateBookedDateEvent = {}
            )
        }
    }
}