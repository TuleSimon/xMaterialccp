package com.simon.xmaterialccp.component

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.simon.xmaterialccp.data.CountryData
import com.simon.xmaterialccp.data.utils.getNumberHint
import com.simon.xmaterialccp.transformation.PhoneNumberTransformation
import com.simon.xmaterialccp.R


/**
 * @param countrytextstyle the textstyle to be used for the country in the lazy column in dialog
 *@param dialogcountrycodetextstyle the textstyle to be used for the country code in the lazy column in dialog
 * @param showCountryCodeInDIalog whether to show the country code in the lazy column dialog or not
 * @param countrycodetextstyle the textstyle to be used for the country code in the original textfield
 * @param showDropDownAfterFlag whether to show the drop down before or after the flag, if true then the dropdown will show after flag, if false before flag
 * @param dropDownIconTInt the icon tint to be used for the dropdown icon
 * @param text the dialog phone number text
 * @param onValueChange the function to be called whenever the phone number changes
 * @param showCountryCode If the country code should be shown on the textfield, if `false` and [showCountryFlag] is `true` just the flag will be shown
 * @param showCountryFlag
 * @param defaultCountry The default country to be showed when the ccp is first initialized without any selection
 * @param pickedCountry the function to be called whenever a country is selected with a parameter of [CountryData] that sets the select country data to the parameter passed
 * @param focusedBorderColor the border color when textfield is focused
 * @param unfocusedBorderColor the border color when textfield is unfocused
 * @param cursorColor The color to be used for the cursor on the textfield
 * @param dialogAppBarColor The background color of the topappbar
 * @param error if to switch textfield to error state, if `true` the textfield shows red outlines and an error icon
 * @param showErrorText if to show an errortext that appears below the textfield when an error occurs
 * @param flagPadding the padding values of the textfield from the flag
 * @param surfaceColor The color to be used as surface background for the country lazy column
 * @param countryItemBgColor The background color of the country item in the lazy column
 * @param countryItemBgShape The shape to be used for a row in the lazy column
 * @param phonenumbertextstyle the textstyle for the phone number on the textfield
 * @param phonehintnumbertextstyle the textstyle for the phone number hint on the textfield
 * @param searchFieldPlaceHolderTextStyle the placeholder on the searchfield text style
 * @param searchFieldTextStyle the search textfield text style
 * @param errorTextStyle the style to be used for the error text
 * @param appbartitleStyle the style to be used for the title on the appbar
 * @param searchFieldBackgroundColor the background color of the search field
 * @param searchFieldShapeCornerRadiusInPercentage the searchfield shape corner radius
 * @param textFieldShapeCornerRadiusInPercentage the textfield shape corner radius
 * @param dialogNavIconColor the navigation icon on the appbar tint color
 * @param countryItemVerticalPadding the vertical padding for the country item on the lazy column
 * @param countryItemHorizontalPadding the horizontal padding for the country item on the lazy column
 * @param isEnabled to make the cccp to be enabled or disabled, if disabled the ccp can not be edited
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialCountryCodePicker(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    showCountryCode: Boolean = true,
    showCountryFlag: Boolean = true,
    defaultCountry: CountryData,
    pickedCountry: (CountryData) -> Unit,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.onSecondary,
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    dialogAppBarColor: Color = MaterialTheme.colorScheme.primary,
    error: Boolean=false,
    showErrorText: Boolean=true,
    flagPadding: PaddingValues = PaddingValues(horizontal = 10.dp),
    surfaceColor:Color = MaterialTheme.colorScheme.surface,
    countryItemBgColor:Color = Color.Unspecified,
    countryItemBgShape:RoundedCornerShape = RoundedCornerShape(0.dp),
    phonenumbertextstyle:TextStyle = MaterialTheme.typography.bodyMedium,
    phonehintnumbertextstyle:TextStyle = MaterialTheme.typography.bodyMedium,
    searchFieldPlaceHolderTextStyle:TextStyle = MaterialTheme.typography.bodyMedium,
    searchFieldTextStyle:TextStyle = MaterialTheme.typography.bodyMedium,
    searchFieldBackgroundColor:Color = MaterialTheme.colorScheme.background.copy(0.7f),
    searchFieldShapeCornerRadiusInPercentage:Int = 30,
    textFieldShapeCornerRadiusInPercentage:Int = 30,
    errorTextStyle:TextStyle = MaterialTheme.typography.bodyMedium,
    dialogNavIconColor: Color = MaterialTheme.colorScheme.onBackground,
    appbartitleStyle :TextStyle = MaterialTheme.typography.titleLarge,
    countryItemVerticalPadding: Dp = 8.dp,
    countryItemHorizontalPadding: Dp = 8.dp,
    countrytextstyle:TextStyle = MaterialTheme.typography.bodyMedium,
    dialogcountrycodetextstyle:TextStyle = MaterialTheme.typography.bodyMedium,
    showCountryCodeInDIalog:Boolean = true,
    countrycodetextstyle:TextStyle = MaterialTheme.typography.bodyMedium,
    showDropDownAfterFlag:Boolean = false,
    dropDownIconTInt:Color = MaterialTheme.colorScheme.onBackground,
    isEnabled:Boolean = true
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
                modifier = modifier.
                padding()
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(textFieldShapeCornerRadiusInPercentage))
                    .clipToBounds(),
                shape = RoundedCornerShape(textFieldShapeCornerRadiusInPercentage),
                value = textFieldValue,
                textStyle =  phonenumbertextstyle,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = if (error) Color.Red else focusedBorderColor,
                    unfocusedBorderColor = if (error) Color.Red else unfocusedBorderColor,
                    cursorColor = cursorColor,
                    textColor = phonenumbertextstyle.color
                ),
                onValueChange = {
                    textFieldValueState = it
                    if (text != it.text) {
                        onValueChange(it.text)
                    }
                },
                readOnly = isEnabled,
                singleLine = true,
                visualTransformation = PhoneNumberTransformation(defaultCountry.countryCode.uppercase()),
                placeholder = { Text( style= phonehintnumbertextstyle,
                    text = stringResource(id = getNumberHint(defaultCountry.countryCode))) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.NumberPassword,
                    autoCorrect = true,
                ),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hideSoftwareKeyboard() }),
                leadingIcon = {
                        Column(modifier = Modifier.padding(flagPadding)) {
                            val dialog = MaterialCodePicker()
                            dialog.MaterialCodeDialog(
                                pickedCountry = pickedCountry,
                                defaultSelectedCountry = defaultCountry,
                                dialogAppBarColor = dialogAppBarColor,
                                showCountryCode = showCountryCode,
                                searchFieldPlaceHolderTextStyle = searchFieldPlaceHolderTextStyle,
                                searchFieldBackgroundColor = searchFieldBackgroundColor,
                                searchFieldTextStyle = searchFieldTextStyle,
                                surfaceColor = surfaceColor,
                                showCountryFlag = showCountryFlag,
                                searchFieldShapeCornerRadiusInPercentage = searchFieldShapeCornerRadiusInPercentage,
                                dialogNavIconColor = dialogNavIconColor,
                                appbartitleStyle = appbartitleStyle,
                                cursorColor = cursorColor,
                                countryItemBgColor = countryItemBgColor,
                                countryItemBgShape = countryItemBgShape,
                                countryItemVerticalPadding = countryItemVerticalPadding,
                                countryItemHorizontalPadding = countryItemHorizontalPadding,
                                countrytextstyle =  countrytextstyle,
                                dialogcountrycodetextstyle = dialogcountrycodetextstyle,
                                showCountryCodeInDIalog = showCountryCodeInDIalog,
                                countrycodetextstyle = countrycodetextstyle,
                                showDropDownAfterFlag =     showDropDownAfterFlag,
                                dropDownIconTInt = dropDownIconTInt,
                                isEnabled = isEnabled,
                            )
                        }


                },
                trailingIcon = {
                    if (error)
                        Icon(
                            imageVector = Icons.Filled.Warning, contentDescription = "Error",
                            tint = MaterialTheme.colorScheme.error
                        )
                }
            )
        }
        if (error && showErrorText)
            Text(
                text = stringResource(id = R.string.invalid_number),
                color = MaterialTheme.colorScheme.error,
                style = errorTextStyle,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 0.8.dp, start = 4.dp)
            )
    }

}