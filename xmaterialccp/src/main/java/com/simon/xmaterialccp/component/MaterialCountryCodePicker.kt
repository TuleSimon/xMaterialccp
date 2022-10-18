package com.simon.xmaterialccp.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.simon.xmaterialccp.data.CountryData
import com.simon.xmaterialccp.data.utils.getNumberHint
import com.simon.xmaterialccp.transformation.PhoneNumberTransformation
import com.simon.xmaterialccp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialCountryCodePicker(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    showCountryCode: Boolean = true,
    defaultCountry: CountryData,
    pickedCountry: (CountryData) -> Unit,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.onSecondary,
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    dialogAppBarColor: Color = MaterialTheme.colorScheme.primary,
    dialogAppBarTextColor: Color = Color.White,
    error: Boolean,
    rowPadding: PaddingValues = PaddingValues(vertical = 10.dp, horizontal = 10.dp),
    surfaceColor:Color = MaterialTheme.colorScheme.surface,
    countryTextColor:Color = MaterialTheme.colorScheme.onBackground,
    countryCodeTextColor:Color = MaterialTheme.colorScheme.onBackground,
    numberTextColor:Color = MaterialTheme.colorScheme.onBackground,
    searchFieldPlaceHolderTextColor:Color = MaterialTheme.colorScheme.onBackground,
    searchFieldTextColor:Color = MaterialTheme.colorScheme.onBackground,
    searchFieldBackgroundColor:Color = MaterialTheme.colorScheme.background.copy(0.7f),
    searchFieldShapeCornerRadiusInPercentage:Int = 30,
    textFieldShapeCornerRadiusInPercentage:Int = 30,
    appbariconsTint:Color = MaterialTheme.colorScheme.onBackground,
    errorTextStyle:TextStyle = MaterialTheme.typography.bodyMedium
) {
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = text)) }
    val textFieldValue = textFieldValueState.copy(text = text)
    val keyboardController = LocalTextInputService.current
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.clipToBounds()
            )
            {
                OutlinedTextField(
                    modifier = modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(textFieldShapeCornerRadiusInPercentage))
                        .clipToBounds().padding(rowPadding),
                    shape = RoundedCornerShape(textFieldShapeCornerRadiusInPercentage),
                    value = textFieldValue,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (!error) Color.Red else focusedBorderColor,
                        unfocusedBorderColor = if (!error) Color.Red else unfocusedBorderColor,
                        cursorColor = cursorColor,
                        textColor = numberTextColor
                    ),
                    onValueChange = {
                        textFieldValueState = it
                        if (text != it.text) {
                            onValueChange(it.text)
                        }
                    },
                    singleLine = true,
                    visualTransformation = PhoneNumberTransformation(defaultCountry.countryCode.uppercase()),
                    placeholder = { Text(text = stringResource(id = getNumberHint(defaultCountry.countryCode))) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.NumberPassword,
                        autoCorrect = true,
                    ),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hideSoftwareKeyboard() }),
                    leadingIcon = {
                        Row {
                            Column {
                                val dialog = MaterialCodePicker()
                                dialog.MaterialCodeDialog(
                                    pickedCountry = pickedCountry,
                                    defaultSelectedCountry = defaultCountry,
                                    dialogAppBarColor = dialogAppBarColor,
                                    showCountryCode = showCountryCode,
                                    dialogAppBarTextColor = dialogAppBarTextColor,
                                    iconsTint = appbariconsTint,
                                    searchFieldTextColor = searchFieldTextColor,
                                    searchFieldPlaceHolderTextColor = searchFieldPlaceHolderTextColor,
                                    searchFieldBackgroundColor = searchFieldBackgroundColor,
                                    countryCodeTextColor = countryCodeTextColor,
                                    countryTextColor = countryTextColor,
                                    surfaceColor = surfaceColor,
                                    searchFieldShapeCornerRadiusInPercentage = searchFieldShapeCornerRadiusInPercentage,
                                )
                            }

                        }
                    },
                    trailingIcon = {
                        if (!error)
                            Icon(
                                imageVector = Icons.Filled.Warning, contentDescription = "Error",
                                tint = MaterialTheme.colorScheme.error
                            )
                    }
                )
            }
            if (!error)
                Text(
                    text = stringResource(id = R.string.invalid_number),
                    color = MaterialTheme.colorScheme.error,
                    style = errorTextStyle,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 0.8.dp)
                )
        }

}