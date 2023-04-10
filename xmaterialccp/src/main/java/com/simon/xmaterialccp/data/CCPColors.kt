package com.simon.xmaterialccp.data


import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * default colors for the ccp picker
 * @param primaryColor primary color for the picker
 * @param   errorColor error color for the picker
 * @param backgroundColor background color for the picker
 * @param surfaceColor the color of surfaces, like cards etc
 * @param outlineColor the outline of the textfield
 * @param disabledOutlineColor the outline of the textfield when disabled
 * @param unfocusedOutlineColor the outline of the textfield when unfocused
 * @param textColor the color of the text in the textfield
 * @param cursorColor the color of the cursor on the textfield
 * @param topAppBarColor the color of the top app bar on the search dialog
 * @param countryItemBgColor the bg color of the country items displayed during search
 * @param searchFieldBgColor the bg color of the searchtext field
 * @param dialogNavIconColor the bg color of the back icon shown on the search dialog
 * @param dropDownIconTint the tint of the drop down icon
 */
@Composable
fun ccpDefaultColors(
    primaryColor: Color = colorScheme.primary,
    errorColor: Color = colorScheme.error,
    backgroundColor: Color = colorScheme.background,
    surfaceColor: Color = colorScheme.surface,
    outlineColor: Color = colorScheme.outline,
    disabledOutlineColor: Color = colorScheme.outline.copy(0.2f),
    unfocusedOutlineColor: Color = colorScheme.outline,
    textColor: Color = colorScheme.onBackground,
    cursorColor: Color = colorScheme.primary,
    topAppBarColor: Color = colorScheme.surface,
    countryItemBgColor: Color = colorScheme.surface,
    searchFieldBgColor: Color = colorScheme.surface,
    dialogNavIconColor: Color = colorScheme.onBackground.copy(0.7f),
    dropDownIconTint: Color = colorScheme.onBackground.copy(0.7f),
): CCPColors {
    return CCPColors(
        primaryColor = primaryColor,
        errorColor = errorColor,
        backgroundColor = backgroundColor,
        surfaceColor = surfaceColor,
        outlineColor = outlineColor,
        disabledOutlineColor = disabledOutlineColor,
        unfocusedOutlineColor = unfocusedOutlineColor,
        textColor = textColor,
        cursorColor = cursorColor,
        topAppBarColor = topAppBarColor,
        countryItemBgColor = countryItemBgColor,
        searchFieldBgColor = searchFieldBgColor,
        dialogNavIconColor = dialogNavIconColor,
        dropDownIconTint = dropDownIconTint,
    )
}


data class CCPColors(
    val primaryColor: Color,
    val errorColor: Color,
    val backgroundColor: Color,
    val surfaceColor: Color,
    val outlineColor: Color,
    val disabledOutlineColor: Color,
    val unfocusedOutlineColor: Color,
    val textColor: Color,
    val cursorColor: Color,
    val topAppBarColor: Color,
    val countryItemBgColor: Color,
    val searchFieldBgColor: Color,
    val dialogNavIconColor: Color,
    val dropDownIconTint: Color,
)
