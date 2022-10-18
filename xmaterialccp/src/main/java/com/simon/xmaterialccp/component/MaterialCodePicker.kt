package com.simon.xmaterialccp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

class MaterialCodePicker {
    @OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun MaterialCodeDialog(
        modifier: Modifier = Modifier,
        padding: Dp = 15.dp,
        defaultSelectedCountry: CountryData = getLibCountries().first(),
        showCountryCode: Boolean = true,
        surfaceColor:Color = MaterialTheme.colorScheme.surface,
        countryTextColor:Color = MaterialTheme.colorScheme.onBackground,
        countryCodeTextColor:Color = MaterialTheme.colorScheme.onBackground,
        searchFieldPlaceHolderTextColor:Color = MaterialTheme.colorScheme.onBackground,
        searchFieldTextColor:Color = MaterialTheme.colorScheme.onBackground,
        searchFieldBackgroundColor:Color = MaterialTheme.colorScheme.background.copy(0.7f),
        searchFieldShapeCornerRadiusInPercentage:Int = 50,
        iconsTint:Color = MaterialTheme.colorScheme.onBackground,
        pickedCountry: (CountryData) -> Unit,
        cursorColor: Color = MaterialTheme.colorScheme.primary,
        dialogAppBarColor: Color = MaterialTheme.colorScheme.primary,
        dialogAppBarTextColor: Color = Color.White,
        countryCodeFontSize:TextUnit = 16.sp
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
                ) { isOpenDialog = true },
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = modifier.width(34.dp),
                    painter = painterResource(
                        id = getFlags(
                            isPickCountry.countryCode
                        )
                    ), contentDescription = null
                )
                if (showCountryCode) {
                    Text(
                        text = isPickCountry.countryPhoneCode,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 6.dp),
                        color = countryCodeTextColor
                    )
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "arrow down")
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
                                if (!isSearch) {
                                    Text(
                                        text = stringResource(id = R.string.select_country),
                                        textAlign = TextAlign.Center,
                                        modifier = modifier.fillMaxWidth()
                                    )
                                } else {
                                    SearchTextField(
                                        value = searchValue,
                                        onValueChange = { searchValue = it },
                                        textColor = searchFieldTextColor,
                                        leadingIcon = {
                                            Icon(
                                                Icons.Filled.Search,
                                                null,
                                                tint = searchFieldPlaceHolderTextColor,
                                                modifier = Modifier.padding(horizontal = 3.dp),
                                            )
                                        },
                                        cursorColor = cursorColor,
                                        hintextColor = searchFieldPlaceHolderTextColor,
                                        trailingIcon = null,
                                        modifier = Modifier
                                            .background(
                                                searchFieldBackgroundColor,
                                                RoundedCornerShape(searchFieldShapeCornerRadiusInPercentage)
                                            )
                                            .height(40.dp),
                                        fontSize = countryCodeFontSize,
                                    )
                                }
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    isOpenDialog = false
                                    searchValue = ""
                                    isSearch = false
                                }) {
                                    Icon(
                                        imageVector = Icons.Rounded.ArrowBack,
                                        contentDescription = "Back",
                                        tint = iconsTint
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.mediumTopAppBarColors(
                                containerColor =dialogAppBarColor ,
                                titleContentColor = dialogAppBarTextColor,
                                actionIconContentColor = iconsTint
                            ),
                            actions = {
                                IconButton(onClick = {
                                    isSearch = !isSearch
                                }) {
                                    Icon(
                                        imageVector = if(isSearch) Icons.Rounded.Close else Icons.Rounded.Search,
                                        contentDescription = "Search/Cancel",
                                        tint = iconsTint
                                    )
                                }
                            }
                        )
                    }
                ) {
                    it.calculateTopPadding()
                    Surface(modifier = modifier.fillMaxSize().background(surfaceColor)) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column {
                                LazyColumn {
                                    items(
                                        (if (searchValue.isEmpty()) {
                                            countryList
                                        } else {
                                            countryList.searchCountry(
                                                searchValue,
                                                context = context
                                            )
                                        })
                                    ) { countryItem ->
                                        Row(
                                            Modifier
                                                .padding(
                                                    horizontal = 18.dp,
                                                    vertical = 18.dp
                                                )
                                                .fillMaxWidth()
                                                .clickable {
                                                    pickedCountry(countryItem)
                                                    isPickCountry = countryItem
                                                    isOpenDialog = false
                                                    searchValue = ""
                                                    isSearch = false
                                                },
                                            horizontalArrangement = Arrangement.Start,
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
                                                text =stringResource(id = getCountryName(countryItem.countryCode.lowercase())),
                                                color = countryTextColor,
                                                modifier =Modifier.padding(horizontal = 18.dp)
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
    }

    @Composable
    private fun SearchTextField(
        modifier: Modifier = Modifier,
        leadingIcon: (@Composable () -> Unit)? = null,
        trailingIcon: (@Composable () -> Unit)? = null,
        value: String,
        textColor: Color = Color.Black,
        hintextColor: Color = Color.Black,
        onValueChange: (String) -> Unit,
        hint: String = stringResource(id = R.string.search),
        cursorColor: Color,
        fontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize
    ) {
        BasicTextField(modifier = modifier
            .fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            cursorBrush = SolidColor(cursorColor),
            textStyle = LocalTextStyle.current.copy(
                color = textColor,
                fontSize = fontSize
            ),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leadingIcon != null) leadingIcon()
                    Box(Modifier.weight(1f)) {
                        if (value.isEmpty()) Text(
                            hint,
                            style = LocalTextStyle.current.copy(
                                color = hintextColor,
                                fontSize = fontSize
                            )
                        )
                        innerTextField()
                    }
                    if (trailingIcon != null) trailingIcon()
                }
            }
        )
    }
}