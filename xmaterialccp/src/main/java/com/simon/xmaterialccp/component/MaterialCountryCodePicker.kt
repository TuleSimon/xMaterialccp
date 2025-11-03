package com.simon.xmaterialccp.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.simon.xmaterialccp.R
import com.simon.xmaterialccp.data.CCPColors
import com.simon.xmaterialccp.data.CountryData
import com.simon.xmaterialccp.data.utils.getNumberHint
import com.simon.xmaterialccp.transformation.PhoneNumberTransformation


/**
 * @param countrytextstyle the textstyle to be used for the country in the lazy column in dialog
 *@param dialogcountrycodetextstyle the textstyle to be used for the country code in the lazy column in dialog
 * @param showCountryCodeInDIalog whether to show the country code in the lazy column dialog or not
 * @param countrycodetextstyle the textstyle to be used for the country code in the original textfield
 * @param showDropDownAfterFlag whether to show the drop down before or after the flag, if true then the dropdown will show after flag, if false before flag
 * @param text the dialog phone number text
 * @param onValueChange the function to be called whenever the phone number changes
 * @param showCountryCode If the country code should be shown on the textfield, if `false` and [showCountryFlag] is `true` just the flag will be shown
 * @param showCountryFlag
 * @param defaultCountry The default country to be showed when the ccp is first initialized without any selection
 * @param pickedCountry the function to be called whenever a country is selected with a parameter of [CountryData] that sets the select country data to the parameter passed
 * @param error if to switch textfield to error state, if `true` the textfield shows red outlines and an error icon
 * @param showErrorText if to show an errortext that appears below the textfield when an error occurs
 * @param flagPadding the padding values of the textfield from the flag
 * @param countryItemBgShape The shape to be used for a row in the lazy column
 * @param phonenumbertextstyle the textstyle for the phone number on the textfield
 * @param phonehintnumbertextstyle the textstyle for the phone number hint on the textfield
 * @param searchFieldPlaceHolderTextStyle the placeholder on the searchfield text style
 * @param searchFieldTextStyle the search textfield text style
 * @param errorTextStyle the style to be used for the error text
 * @param appbartitleStyle the style to be used for the title on the appbar
 * @param searchFieldShapeCornerRadiusInPercentage the searchfield shape corner radius
 * @param textFieldShapeCornerRadiusInPercentage the textfield shape corner radius
 * @param countryItemVerticalPadding the vertical padding for the country item on the lazy column
 * @param countryItemHorizontalPadding the horizontal padding for the country item on the lazy column
 * @param isEnabled to make the cccp to be enabled or disabled, if disabled the ccp can not be edited
 * @param isReadOnly to make the textfield to be enabled or disabled, if disabled the ccp can not be edited
 * @param flagShape to customized the shape of the flag
 * @param showErrorIcon whether to show the error icon
 * @param errorIcon the drawable resource file to use as error icon
 * @param errorText the text to show when an invalid number is entered
 * @param errorModifier modifier applied to the error text
 * @param showClearIcon whether to show clear icon on the textfield
 * @param clearIcon pass a custom clear icon if possible
 * @param onDone keyboardIme onDone Event
 * @param placeholder custom placeholder
 * @param dialogItemBuilder pass this value to be customized the country list item design on the dialog
 * @param colors the colors of the picker, customized the look and feel of the picker
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MaterialCountryCodePicker(
    text: String,
    onValueChange: (String) -> Unit,
    colors: CCPColors,
    defaultCountry: CountryData,
    modifier: Modifier = Modifier,
    pickedCountry: (CountryData) -> Unit,
    showCountryCode: Boolean = true,
    showCountryFlag: Boolean = true,
    error: Boolean = false,
    showErrorText: Boolean = true,
    flagPadding: PaddingValues = PaddingValues(horizontal = 10.dp),
    countryItemBgShape: RoundedCornerShape = RoundedCornerShape(0.dp),
    phonenumbertextstyle: TextStyle = MaterialTheme.typography.bodyMedium,
    phonehintnumbertextstyle: TextStyle = MaterialTheme.typography.bodyMedium,
    searchFieldPlaceHolderTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    searchFieldTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    searchFieldShapeCornerRadiusInPercentage: Int = 30,
    textFieldShapeCornerRadiusInPercentage: Int = 30,
    errorTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    appbartitleStyle: TextStyle = MaterialTheme.typography.titleLarge,
    countryItemVerticalPadding: Dp = 8.dp,
    countryItemHorizontalPadding: Dp = 8.dp,
    countrytextstyle: TextStyle = MaterialTheme.typography.bodyMedium,
    dialogcountrycodetextstyle: TextStyle = MaterialTheme.typography.bodyMedium,
    showCountryCodeInDIalog: Boolean = true,
    countrycodetextstyle: TextStyle = MaterialTheme.typography.bodyMedium,
    showDropDownAfterFlag: Boolean = false,
    isEnabled: Boolean = true,
    isReadOnly: Boolean = false,
    flagShape: CornerBasedShape = RoundedCornerShape(0.dp),
    @DrawableRes errorIcon:Int?=null ,
    @DrawableRes dropDownIcon:Int?=null ,
    showErrorIcon:Boolean=true,
    errorText:String = stringResource(id = R.string.invalid_number),
    errorModifier:Modifier = Modifier,
    onDone: () -> Unit = {},
    placeholder: (@Composable () -> Unit)? = null,
    showClearIcon: Boolean = false,
    dialogItemBuilder: @Composable() ((data: CountryData, onClick: () -> Unit) -> Unit)? = null,
    @DrawableRes clearIcon: Int? = null,

    ) {
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = text)) }
    val textFieldValue = textFieldValueState.copy(text = text)
    val keyboardController = LocalSoftwareKeyboardController.current


    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.clipToBounds()
        )
        {
            OutlinedTextField(
                modifier = modifier
                    .padding()
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(textFieldShapeCornerRadiusInPercentage))
                    .clipToBounds(),
                shape = RoundedCornerShape(textFieldShapeCornerRadiusInPercentage),
                value = textFieldValue,
                textStyle = phonenumbertextstyle,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (error) colors.errorColor else if (isEnabled) colors.outlineColor
                    else colors.unfocusedOutlineColor,
                    unfocusedBorderColor = if (error) colors.errorColor else colors.unfocusedOutlineColor,
                    cursorColor =  colors.cursorColor,
                    focusedContainerColor = colors.surfaceColor

                ),
                onValueChange = {
                    textFieldValueState = it
                    if (text != it.text) {
                        onValueChange(it.text)
                    }
                },
                readOnly = isReadOnly,
                enabled = isEnabled,
                singleLine = true,
                visualTransformation = PhoneNumberTransformation(defaultCountry.countryCode.uppercase()),
                placeholder = {
                    if(placeholder!=null){
                        placeholder()
                    } else{
                        Text(
                            style = phonehintnumbertextstyle,
                            text = stringResource(id = getNumberHint(defaultCountry.countryCode))
                        )
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.NumberPassword,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        onDone()
                    }
                ),
                leadingIcon = {
                    Column(modifier = Modifier.padding(flagPadding)) {
                        val dialog = MaterialCodePicker()
                        dialog.MaterialCodeDialog(
                            pickedCountry = pickedCountry,
                            defaultSelectedCountry = defaultCountry,
                            showCountryCode = showCountryCode,
                            searchFieldPlaceHolderTextStyle = searchFieldPlaceHolderTextStyle,
                            searchFieldTextStyle = searchFieldTextStyle,
                            showCountryFlag = showCountryFlag,
                            searchFieldShapeCornerRadiusInPercentage = searchFieldShapeCornerRadiusInPercentage,
                            appbartitleStyle = appbartitleStyle,
                            countryItemBgShape = countryItemBgShape,
                            countryItemVerticalPadding = countryItemVerticalPadding,
                            countryItemHorizontalPadding = countryItemHorizontalPadding,
                            countrytextstyle = countrytextstyle,
                            dialogcountrycodetextstyle = dialogcountrycodetextstyle,
                            showCountryCodeInDIalog = showCountryCodeInDIalog,
                            countrycodetextstyle = countrycodetextstyle,
                            showDropDownAfterFlag = showDropDownAfterFlag,
                            colors = colors,
                            dropDownIcon = dropDownIcon,
                            flagShape = flagShape,
                            isEnabled = isEnabled,
                            dialogItemBuilder = dialogItemBuilder,
                        )
                    }
                },
                trailingIcon = {
                    if ((error.not() || showErrorIcon.not()) && showClearIcon && textFieldValue.text.isNotEmpty()) {
                        if (clearIcon == null) {
                            Icon(
                                painterResource(R.drawable.outline_close_24) ,
                                contentDescription = "Error",
                                tint = colors.clearIconColor,
                                modifier = Modifier.clickable {
                                    textFieldValueState = textFieldValueState.copy(text = "")
                                    onValueChange("")
                                }
                            )
                        } else {
                            Icon(
                                painterResource(id = clearIcon),
                                contentDescription = stringResource(R.string.clear_icon),
                                tint = colors.clearIconColor,
                                modifier = Modifier.clickable {
                                    textFieldValueState = textFieldValueState.copy(text = "")
                                    onValueChange("")
                                }
                            )
                        }
                    }
                    if (error && showErrorIcon) {
                        if (errorIcon == null) {
                            Icon(
                                painterResource(R.drawable.outline_warning_24),
                                contentDescription = stringResource(
                                    R.string.error_icon
                                ),
                                tint = colors.errorColor
                            )
                        } else {
                            Icon(
                                painterResource(id = errorIcon), contentDescription = "Error",
                                tint = colors.errorColor
                            )
                        }
                    }
                }
            )
        }
        if (error && showErrorText) {
            Text(
                text = errorText,
                color = colors.errorColor,
                style = errorTextStyle,
                modifier = errorModifier
            )
        }
    }

}