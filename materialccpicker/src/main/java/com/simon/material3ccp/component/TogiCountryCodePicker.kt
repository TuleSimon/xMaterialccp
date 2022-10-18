package com.simon.material3ccp.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.simon.material3ccp.data.CountryData
import com.simon.material3ccp.data.utils.getNumberHint
import com.simon.material3ccp.transformation.PhoneNumberTransformation
import com.simon.material3ccp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TogiCountryCodePicker(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.background,
    showCountryCode: Boolean = true,
    defaultCountry: CountryData,
    pickedCountry: (CountryData) -> Unit,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.onSecondary,
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    dialogAppBarColor: Color = MaterialTheme.colorScheme.primary,
    dialogAppBarTextColor: Color = Color.White,
    error: Boolean,
    rowPadding: Modifier = modifier.padding(vertical = 16.dp, horizontal = 16.dp)
) {
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = text)) }
    val textFieldValue = textFieldValueState.copy(text = text)
    val keyboardController = LocalTextInputService.current
    Surface(
        color = color
    ) {
        Column(
            modifier = rowPadding
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                OutlinedTextField(
                    modifier = modifier
                        .fillMaxWidth(),

                    value = textFieldValue,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (!error) Color.Red else focusedBorderColor,
                        unfocusedBorderColor = if (!error) Color.Red else unfocusedBorderColor,
                        cursorColor = cursorColor
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
                                val dialog = TogiCodePicker()
                                dialog.TogiCodeDialog(
                                    pickedCountry = pickedCountry,
                                    defaultSelectedCountry = defaultCountry,
                                    dialogAppBarColor = dialogAppBarColor,
                                    showCountryCode = showCountryCode,
                                    dialogAppBarTextColor = dialogAppBarTextColor
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
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 0.8.dp)
                )
        }
    }
}