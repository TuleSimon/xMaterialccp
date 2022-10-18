package com.simon.material3ccp.component

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
import com.simon.material3ccp.data.CountryData
import com.simon.material3ccp.data.utils.getCountryName
import com.simon.material3ccp.data.utils.getFlags
import com.simon.material3ccp.data.utils.getLibCountries
import com.simon.material3ccp.utils.searchCountry
import com.simon.material3ccp.R

class TogiCodePicker {
    @OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun TogiCodeDialog(
        modifier: Modifier = Modifier,
        padding: Dp = 15.dp,
        defaultSelectedCountry: CountryData = getLibCountries().first(),
        showCountryCode: Boolean = true,
        pickedCountry: (CountryData) -> Unit,
        dialogAppBarColor: Color = MaterialTheme.colorScheme.primary,
        dialogAppBarTextColor: Color = Color.White,
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
                        modifier = Modifier.padding(start = 6.dp)
                    )
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
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
                                        textColor = dialogAppBarTextColor,
                                        leadingIcon = {
                                            Icon(
                                                Icons.Filled.Search,
                                                null,
                                                tint = dialogAppBarTextColor,
                                                modifier = Modifier.padding(horizontal = 3.dp),
                                            )
                                        },
                                        trailingIcon = null,
                                        modifier = Modifier
                                            .background(
                                                dialogAppBarColor.copy(0.6f),
                                            )
                                            .clip(RoundedCornerShape(50))
                                            .height(40.dp),
                                        fontSize = 16.sp,
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
                                        contentDescription = "Back"
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.mediumTopAppBarColors(
                                containerColor =dialogAppBarColor ,
                                titleContentColor = dialogAppBarTextColor,
                                actionIconContentColor = dialogAppBarTextColor
                            ),
                            actions = {
                                IconButton(onClick = {
                                    isSearch = !isSearch
                                }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Search,
                                        contentDescription = "Search"
                                    )
                                }
                            }
                        )
                    }
                ) {
                    it.calculateTopPadding()
                    Surface(modifier = modifier.fillMaxSize()) {
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
                                                stringResource(id = getCountryName(countryItem.countryCode.lowercase())),
                                                Modifier.padding(horizontal = 18.dp)
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
        onValueChange: (String) -> Unit,
        hint: String = stringResource(id = R.string.search),
        fontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize
    ) {
        BasicTextField(modifier = modifier
            .background(
                MaterialTheme.colorScheme.surface,
                MaterialTheme.shapes.small,
            )
            .fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            textStyle = LocalTextStyle.current.copy(
                color = textColor,
                fontSize = fontSize
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leadingIcon != null) leadingIcon()
                    Box(Modifier.weight(1f)) {
                        if (value.isEmpty()) Text(
                            hint,
                            style = LocalTextStyle.current.copy(
                                color = textColor,
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