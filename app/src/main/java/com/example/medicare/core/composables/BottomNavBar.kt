package com.example.dispensary.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medicare.R
import com.example.medicare.ui.theme.primary_container
import com.example.medicare.ui.theme.tint

data class BottomNavigationItem(
    @StringRes val label: Int,
    val route: String,
)

val listOfOutlinedIcons = listOf(
    R.drawable.ic_home_outlined,
    R.drawable.ic_medicare_outlined,
    R.drawable.ic_appointment_outlined,
    R.drawable.ic_child_outlined,
)
val listOfFilledIcons = listOf(
    R.drawable.ic_home_filled,
    R.drawable.ic_medicare_filled,
    R.drawable.ic_appointment_filled,
    R.drawable.ic_child_filled,
)


val bottomNavItems = listOf(
    BottomNavigationItem(
        label = R.string.home,
        route = "Home"
    ),
    BottomNavigationItem(
        label = R.string.vaccines,
        route = "Vaccine"
    ),
    BottomNavigationItem(
        label = R.string.appointments,
        route = "Appointment"
    ),
    BottomNavigationItem(
        label = R.string.children,
        route = "Children"
    ),

    )

@Composable
fun BottomNavBarComponent(
    modifier: Modifier = Modifier,
    onItem1Click: () -> Unit = {},
    onItem2Click: () -> Unit = {},
    onItem3Click: () -> Unit = {},
    onItem4Click: () -> Unit = {},
    selectedIndex: Int = 0,
    bottomNavItems: List<BottomNavigationItem>,
    listOfOutlinedIcons: List<Int>,
    listOfFilledIcons: List<Int>,
) {
    NavigationBar(modifier = modifier) {
        bottomNavItems.forEachIndexed { index, item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = tint,
                    indicatorColor = primary_container
                ),
                selected = selectedIndex == index,
                onClick =
                when (index) {
                    0 -> onItem1Click
                    1 -> onItem2Click
                    2 -> onItem3Click
                    else -> onItem4Click
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = if (selectedIndex == index) listOfFilledIcons[index]
                            else listOfOutlinedIcons[index]
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.label),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun BottomNavBarComponentPreview() {
    MaterialTheme {
        Surface {
            BottomNavBarComponent(
                bottomNavItems = bottomNavItems,
                listOfFilledIcons = listOfFilledIcons,
                listOfOutlinedIcons = listOfOutlinedIcons
            )
        }
    }
}

