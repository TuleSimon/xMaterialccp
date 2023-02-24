# XMaterial Compose Country Code Picker

Jetpack Compose Material Country Code Picker

<a href="https://www.linkedin.com/mwlite/in/tule-simon-67a202157">Chat me up on Linkedln</a>

## Latest Version
The latest version is <a href="https://github.com/TuleSimon/xMaterialccp/releases/tag/v1.12">V1.12</a>

## WHAT IS NEW (v1.12)
* update compose version, and android kotlin version

# CREDIT
This project was forked from <a href="https://github.com/togisoft/jetpack_compose_country_code_picker">togisoft</a>,
His Github repo and efforts are what laid the ground work for me to improve on it and create a more customizable version of his library,


<h1>WELCOME</h1>

If you are looking for a highly customizable and Material Country Phone Code Picker for Jetpack Compose you can use the package.


* Country numbers hints
* Phone number visualTransformation (Automatic number formatting)
* Automatic country recognition (detection by sim card if sim card is inserted)
* With TextField
* Can Customize
* Added language translations
* Added clear text button
* animations when searching
* Dialog changed and customized

Languages:

* Turkish
* English
* Italian
* Arabic
* Russian

New features will be added as time goes by and updates will be made. This project is open source without any profit motive.

For language support, you can send me a message.
<a href="https://www.linkedin.com/mwlite/in/tule-simon-67a202157">Chat me up on Linkedln</a>

<h3>Screenshots</h3>
<div class="row">
  <img src="screenshots/Screenshot_dialog.jpg" width="300"> 
  <img src="screenshots/Screenshot_dialog2.jpg" width="300"> 
  <img src="screenshots/Screenshot_error.jpg" width="300"> 
  <img src="screenshots/Screenshot_roundedccp.jpg" width="300">
 </div>


**** Specifications ****

<h3> Material Country Code Picker </h3>

```kotlin
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
    dropDownIconTInt:Color = MaterialTheme.colorScheme.onBackground
) 
```  



<h3>  Field Usage </h3>

```kotlin
@Composable
fun SelectCountryWithCountryCode() {
    val context = LocalContext.current
    var phoneCode by remember { mutableStateOf(getDefaultPhoneCode(context)) }
    val phoneNumber = rememberSaveable { mutableStateOf("") }
    var defaultLang by rememberSaveable { mutableStateOf(getDefaultLangCode(context)) }
    var isValidPhone by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        MaterialCountryCodePicker(
            pickedCountry = {
                phoneCode = it.countryPhoneCode
                defaultLang = it.countryCode
            },
            defaultCountry = getLibCountries().single { it.countryCode == defaultLang },
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            dialogAppBarColor = Color.White,
            error = !isValidPhone,
            text = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            surfaceColor = MaterialTheme.colorScheme.surface,
            searchFieldPlaceHolderTextStyle = MaterialTheme.typography.bodyMedium,
            searchFieldTextStyle = MaterialTheme.typography.bodyMedium,
            phonenumbertextstyle =  MaterialTheme.typography.bodyMedium,
            countrytextstyle =  MaterialTheme.typography.bodyMedium,
            countrycodetextstyle =  MaterialTheme.typography.bodyMedium,
            showErrorText = true,
            showCountryCodeInDIalog = true,
            showDropDownAfterFlag = true,
            searchFieldBackgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            textFieldShapeCornerRadiusInPercentage = 40,
            searchFieldShapeCornerRadiusInPercentage = 40,
            appbartitleStyle = MaterialTheme.typography.titleLarge,
            countryItemBgShape = RoundedCornerShape(5.dp),
            showCountryFlag = true,
            showCountryCode = true,
            countryItemBgColor = MaterialTheme.colorScheme.secondaryContainer
        )

        val fullPhoneNumber = "$phoneCode${phoneNumber.value}"
        val checkPhoneNumber = checkPhoneNumber(
            phone = phoneNumber.value,
            fullPhoneNumber = fullPhoneNumber,
            countryCode = defaultLang
        )
        OutlinedButton(
            onClick = {
                isValidPhone = checkPhoneNumber
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(
                    50.dp
                )
        ) {
            Text(text = "Phone Verify")
        }
    }
}



```  

<h3><- ********* Extras ********* -></h3>

* *** countrytextstyle *** the textstyle to be used for the country in the lazy column in dialog
* *** dialogcountrycodetextstyle *** the textstyle to be used for the country code in the lazy column in dialog
* *** showCountryCodeInDIalog *** whether to show the country code in the lazy column dialog or not
* *** countrycodetextstyle *** the textstyle to be used for the country code in the original textfield
* *** showDropDownAfterFlag *** whether to show the drop down before or after the flag, if true then the dropdown will show after flag, if false before flag
* *** dropDownIconTInt *** the icon tint to be used for the dropdown icon
* *** text *** the dialog phone number text
* *** onValueChange *** the function to be called whenever the phone number changes
* *** showCountryCode *** If the country code should be shown on the textfield, if `false` and [showCountryFlag] is `true` just the flag will be shown
* *** showCountryFlag ***
* *** defaultCountry *** The default country to be showed when the ccp is first initialized without any selection
* *** pickedCountry *** the function to be called whenever a country is selected with a parameter of [CountryData] that sets the select country data to the parameter passed
* *** focusedBorderColor *** the border color when textfield is focused
* *** unfocusedBorderColor *** the border color when textfield is unfocused
* *** cursorColor *** The color to be used for the cursor on the textfield
* *** dialogAppBarColor *** The background color of the topappbar
* *** error *** if to switch textfield to error state, if `true` the textfield shows red outlines and an error icon
* showErrorText if to show an errortext that appears below the textfield when an error occurs
* flagPadding the padding values on the textfield
* surfaceColor The color to be used as surface background for the country lazy column
* countryItemBgColor The background color of the country item in the lazy column
* countryItemBgShape The shape to be used for a row in the lazy column
* phonenumbertextstyle the textstyle for the phone number on the textfield
* phonehintnumbertextstyle the textstyle for the phone number hint on the textfield
* searchFieldPlaceHolderTextStyle the placeholder on the searchfield text style
* searchFieldTextStyle the search textfield text style
* errorTextStyle the style to be used for the error text
* appbartitleStyle the style to be used for the title on the appbar
* searchFieldBackgroundColor the background color of the search field
* searchFieldShapeCornerRadiusInPercentage the searchfield shape corner radius
* textFieldShapeCornerRadiusInPercentage the textfield shape corner radius
* dialogNavIconColor the navigation icon on the appbar tint color
* countryItemVerticalPadding the vertical padding for the country item on the lazy column
* countryItemHorizontalPadding the horizontal padding for the country item on the lazy column
* isEnabled to make the cccp to be enabled or disabled, if disabled the ccp can not be edited
* isReadOnly to make the cccp to be readonly, if readonly ccp can not be edited



<h3> How to add in your project </h3>

In the build.gradle add maven central repository

```groovy
    repositories {
    maven { url 'https://jitpack.io' }
}

```

Step 2. Add the dependency

```
  dependencies {
	  implementation 'com.github.TuleSimon:xMaterialccp:$latest_version'
	}  
```    


<h3> Addition of new Features or Bug Request </h3>

Feel free to contact me for any feature request or file an issue if a bug is encountered 
