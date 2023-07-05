package com.simon.xmaterialccp.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.simon.xmaterialccp.data.CountryData
import com.simon.xmaterialccp.data.utils.getCountryName
import com.simon.xmaterialccp.data.utils.getFlags
import com.simon.xmaterialccp.data.utils.getLibCountries
import com.simon.xmaterialccp.utils.searchCountry
import com.simon.xmaterialccp.R
import com.simon.xmaterialccp.data.CCPColors

class MaterialCodePicker {
    @OptIn(
        ExperimentalMaterial3Api::class,
        ExperimentalFoundationApi::class
    )
    @Composable
    fun MaterialCodeDialog(
        modifier: Modifier = Modifier,
        padding: Dp = 0.dp,
        defaultSelectedCountry: CountryData = getLibCountries().first(),
        showCountryCode: Boolean = true,
        showCountryFlag: Boolean = true,
        colors: CCPColors,
        searchFieldShapeCornerRadiusInPercentage: Int = 50,
        pickedCountry: (CountryData) -> Unit,
        countryItemVerticalPadding: Dp = 8.dp,
        countryItemBgShape: RoundedCornerShape = RoundedCornerShape(0.dp),
        appbartitleStyle: TextStyle = MaterialTheme.typography.titleLarge,
        countryItemHorizontalPadding: Dp = 8.dp,
        countrytextstyle: TextStyle = MaterialTheme.typography.bodyMedium,
        dialogcountrycodetextstyle: TextStyle = MaterialTheme.typography.bodyMedium,
        showCountryCodeInDIalog: Boolean = true,
        countrycodetextstyle: TextStyle = MaterialTheme.typography.bodyMedium,
        showDropDownAfterFlag: Boolean = false,
        searchFieldPlaceHolderTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
        searchFieldTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
        isEnabled: Boolean = true,
        @DrawableRes dropDownIcon: Int? = null,
        flagShape: CornerBasedShape = RoundedCornerShape(0.dp)
    ) {
        val countryList: List<CountryData> = getLibCountries()
        var isPickCountry by remember { mutableStateOf(defaultSelectedCountry) }
        var isOpenDialog by remember { mutableStateOf(false) }
        var searchValue by remember { mutableStateOf("") }
        var isSearch by remember { mutableStateOf(false) }
        val context = LocalContext.current
        val interactionSource = remember { MutableInteractionSource() }

        Column(
            modifier = Modifier
                .padding(padding)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { if (isEnabled) isOpenDialog = true },
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showCountryFlag) {
                    Image(
                        modifier = modifier
                            .width(26.dp)
                            .background(shape = flagShape, color = Color.Transparent)
                            .clip(flagShape)
                            .clipToBounds(),
                        painter = painterResource(
                            id = getFlags(
                                isPickCountry.countryCode
                            )
                        ),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null
                    )
                }
                if (showDropDownAfterFlag) {
                    IconButton(onClick = {
                        if (isEnabled) isOpenDialog = true
                    }) {
                        if (dropDownIcon == null) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "arrow down",
                                tint = colors.dropDownIconTint
                            )
                        } else {
                            Icon(
                                painterResource(id = dropDownIcon),
                                contentDescription = "arrow down",
                                tint = colors.dropDownIconTint
                            )
                        }
                    }
                }

                if (showCountryCode) {
                    Text(
                        text = isPickCountry.countryPhoneCode,
                        modifier = Modifier.padding(
                            start = 3.dp,
                            end = if (showDropDownAfterFlag) 3.dp else 0.dp
                        ),
                        style = countrycodetextstyle
                    )
                }
                if (!showDropDownAfterFlag) {
                    IconButton(onClick = {
                        if (isEnabled) isOpenDialog = true
                    }) {
                        if (dropDownIcon == null) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "arrow down",
                                tint = colors.dropDownIconTint
                            )
                        } else {
                            Icon(
                                painterResource(id = dropDownIcon),
                                contentDescription = "arrow down",
                                tint = colors.dropDownIconTint
                            )
                        }
                    }
                }
            }
        }

        //Select Country Dialog
        if (isOpenDialog) {
            Dialog(
                onDismissRequest = { isOpenDialog = false },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false
                ),
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Select country/region",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    style = appbartitleStyle
                                )
                            },
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        isOpenDialog = false
                                        searchValue = ""
                                        isSearch = false
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "close",
                                        tint = colors.dialogNavIconColor
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.mediumTopAppBarColors(
                                containerColor = colors.topAppBarColor,
                            ),
                        )
                    }
                ) {
                    Surface(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(top = it.calculateTopPadding()),
                        color = colors.surfaceColor
                    ) {

                        Column() {

                            //searchField
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(10.dp)
                            ) {

                                SearchTextField(
                                    value = searchValue,
                                    onValueChange = { searchValue = it },
                                    hint = stringResource(id = R.string.search),
                                    cursorColor = colors.cursorColor,
                                    searchFieldPlaceHolderTextStyle = searchFieldPlaceHolderTextStyle.copy(
                                        color = colors.textColor.copy(0.7f)
                                    ),
                                    searchFieldTextStyle = searchFieldTextStyle.copy(color = colors.textColor),
                                    trailingIcon = {
                                        if (searchValue.isNotEmpty())
                                            IconButton(onClick = {
                                                searchValue = ""
                                            }, modifier = Modifier.padding(horizontal = 5.dp)) {
                                                Icon(
                                                    imageVector = Icons.Filled.Clear,
                                                    contentDescription = null,
                                                    tint = colors.textColor
                                                )
                                            }
                                        else null
                                    },
                                    modifier = Modifier
                                        .background(
                                            colors.searchFieldBgColor,
                                            RoundedCornerShape(
                                                searchFieldShapeCornerRadiusInPercentage
                                            )
                                        )
                                        .fillMaxWidth()
                                        .height(40.dp),
                                )

                            }


                            //lazy column
                            LazyColumn {
                                items(
                                    (if (searchValue.isEmpty()) {
                                        countryList
                                    } else {
                                        countryList.searchCountry(
                                            searchValue,
                                            context = context
                                        )
                                    }),
                                    key = { it.countryCode }
                                ) { countryItem ->
                                    Row(
                                        Modifier
                                            .padding(
                                                vertical = countryItemVerticalPadding,
                                                horizontal = countryItemHorizontalPadding
                                            )
                                            .background(
                                                color = colors.countryItemBgColor,
                                                shape = countryItemBgShape
                                            )
                                            .animateItemPlacement(
                                            )
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .padding(10.dp)
                                            .clickable {
                                                pickedCountry(countryItem)
                                                isPickCountry = countryItem
                                                isOpenDialog = false
                                                searchValue = ""
                                                isSearch = false
                                            },
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            modifier = modifier.width(30.dp),
                                            painter = painterResource(
                                                id = getFlags(
                                                    countryItem.countryCode
                                                )
                                            ), contentDescription = null
                                        )
                                        Text(
                                            text = stringResource(id = getCountryName(countryItem.countryCode.lowercase())),
                                            maxLines = 1,
                                            style = countrytextstyle,
                                            textAlign = TextAlign.Start,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.widthIn(200.dp)
                                        )
                                        if (showCountryCodeInDIalog)
                                            Text(
                                                text = countryItem.countryPhoneCode,
                                                style = dialogcountrycodetextstyle,
                                            )
                                    }
                                }
                            }

                        }

                    }
                }
            }
        }
    }


    @Composable
    private fun SearchTextField(
        modifier: Modifier = Modifier,
        leadingIcon: (@Composable () -> Unit)? = null,
        trailingIcon: (@Composable () -> Unit)? = null,
        value: String,
        onValueChange: (String) -> Unit,
        hint: String = stringResource(id = R.string.search),
        cursorColor: Color,
        searchFieldTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
        searchFieldPlaceHolderTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    ) {
        BasicTextField(modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            cursorBrush = SolidColor(cursorColor),
            textStyle = searchFieldTextStyle,
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    if (leadingIcon != null) leadingIcon()
                    Box(Modifier.weight(1f)) {
                        if (value.isEmpty()) Text(
                            hint,
                            modifier = Modifier.padding(start = 5.dp),
                            style = searchFieldPlaceHolderTextStyle
                        )
                        innerTextField()
                    }
                    if (trailingIcon != null) trailingIcon()
                }
            }
        )
    }
}
