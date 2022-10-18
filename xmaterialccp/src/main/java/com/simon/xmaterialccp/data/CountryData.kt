package com.simon.xmaterialccp.data

import androidx.annotation.DrawableRes
import com.simon.xmaterialccp.R
import java.util.*

data class CountryData(
    private var cCodes: String,
    val countryPhoneCode: String = "+90",
    val cNames:String = "tr",
    @DrawableRes val flagResID: Int = R.drawable.tr
) {
    val countryCode = cCodes.lowercase(Locale.getDefault())
}
