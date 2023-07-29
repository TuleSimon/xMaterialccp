package com.simon.materialCCPExample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simon.materialCCPExample.ui.theme.MaterialCCPExampleTheme
import com.simon.xmaterialccp.component.MaterialCountryCodePicker
import com.simon.xmaterialccp.data.CCPColors
import com.simon.xmaterialccp.data.ccpDefaultColors
import com.simon.xmaterialccp.data.utils.checkPhoneNumber
import com.simon.xmaterialccp.data.utils.getDefaultLangCode
import com.simon.xmaterialccp.data.utils.getDefaultPhoneCode
import com.simon.xmaterialccp.data.utils.getLibCountries


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialCCPExampleTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    topBar = {
                        TopAppBar(title = { Text(text = "XMaterialCCP Demo") })
                    }
                ) {
                    SelectCountryBody(
                        Modifier
                            .padding(top = it.calculateTopPadding())
                            .fillMaxSize()
                            .padding(15.dp)
                            .verticalScroll(rememberScrollState())
                    )
                }
            }
        }
    }
}

@Composable
fun SelectCountryBody(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SelectCountryWithCountryCode()
    }
}


@Composable
fun SelectCountryWithCountryCode() {
    val context = LocalContext.current
    var phoneCode by remember { mutableStateOf(getDefaultPhoneCode(context)) }
    val phoneNumber = rememberSaveable { mutableStateOf("") }
    var defaultLang by rememberSaveable { mutableStateOf(getDefaultLangCode(context)) }
    var isValidPhone by remember { mutableStateOf(true) }

    MaterialCountryCodePicker(
        pickedCountry = {
            phoneCode = it.countryPhoneCode
            defaultLang = it.countryCode
        },
        defaultCountry = getLibCountries().single { it.countryCode == defaultLang },
        error = !isValidPhone,
        text = phoneNumber.value,
        onValueChange = { phoneNumber.value = it },
        searchFieldPlaceHolderTextStyle = MaterialTheme.typography.bodyMedium,
        searchFieldTextStyle = MaterialTheme.typography.bodyMedium,
        phonenumbertextstyle = MaterialTheme.typography.bodyMedium,
        countrytextstyle = MaterialTheme.typography.bodyMedium,
        countrycodetextstyle = MaterialTheme.typography.bodyMedium,
        showErrorText = true,
        showCountryCodeInDIalog = true,
        showDropDownAfterFlag = true,
        textFieldShapeCornerRadiusInPercentage = 40,
        searchFieldShapeCornerRadiusInPercentage = 40,
        appbartitleStyle = MaterialTheme.typography.titleLarge,
        countryItemBgShape = RoundedCornerShape(5.dp),
        showCountryFlag = true,
        showCountryCode = true,
        flagShape = RoundedCornerShape(10f),
        isEnabled = true,
        showErrorIcon = false,
        colors = ccpDefaultColors(
            primaryColor = MaterialTheme.colorScheme.primary,
            errorColor = MaterialTheme.colorScheme.error,
            backgroundColor = MaterialTheme.colorScheme.background,
            surfaceColor = MaterialTheme.colorScheme.surface,
            outlineColor = MaterialTheme.colorScheme.outline,
            disabledOutlineColor = MaterialTheme.colorScheme.outline.copy(0.1f),
            unfocusedOutlineColor = MaterialTheme.colorScheme.onBackground.copy(0.3f),
            textColor = MaterialTheme.colorScheme.onBackground.copy(0.7f),
            cursorColor = MaterialTheme.colorScheme.primary,
            topAppBarColor = MaterialTheme.colorScheme.surface,
            countryItemBgColor = MaterialTheme.colorScheme.surface,
            searchFieldBgColor = MaterialTheme.colorScheme.surface,
            dialogNavIconColor = MaterialTheme.colorScheme.onBackground.copy(0.7f),
            dropDownIconTint = MaterialTheme.colorScheme.onBackground.copy(0.7f)

        ),
        errorTextStyle = MaterialTheme.typography.bodySmall,
        errorModifier = Modifier.padding(top = 3.dp, start = 10.dp)
    )

    val fullPhoneNumber = "$phoneCode${phoneNumber.value}"
    val checkPhoneNumber = checkPhoneNumber(
        phone = phoneNumber.value,
        fullPhoneNumber = fullPhoneNumber,
        countryCode = defaultLang
    )
    Spacer(modifier = Modifier.height(20.dp))
    Button(
        onClick = {
            isValidPhone = checkPhoneNumber
        },
        modifier = Modifier
            .fillMaxWidth(0.8f)
    ) {
        Text(text = "Phone Verify")
    }
}
