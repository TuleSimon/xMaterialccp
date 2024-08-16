package com.simon.xmaterialccp.data.utils

import android.app.Activity
import android.app.LocaleManager
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.text.intl.Locale
import androidx.core.os.LocaleListCompat
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import com.simon.xmaterialccp.data.CountryData

fun getDefaultLangCode(context: Context): String {
    val localeCode: TelephonyManager =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    val countryCode = localeCode.networkCountryIso
    val defaultLocale = Locale.current.language
    return countryCode?.ifBlank { defaultLocale }?:defaultLocale
}

/**
 * to update the locale of the country picler, you can use this method
 * @param languageCode the language code e.g 'fr'
 */

fun setLocale(context: Context, languageCode: String) {

    val resources = context.resources
    val configuration = resources.configuration
    val newLocale = java.util.Locale(languageCode)
    val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        configuration.locales[0]
    } else {
       configuration.locale
    }

    if(current==newLocale) return


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        // Use LocaleManager for Android 12 and later
        context.getSystemService(LocaleManager::class.java).applicationLocales = LocaleList.forLanguageTags(languageCode)
    } else {
        // For earlier versions, update the app's configuration manually

        java.util.Locale.setDefault(newLocale)

        configuration.setLocale(newLocale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        val newcontext = context.createConfigurationContext(configuration)

    }
    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
    (context.findActivity())?.recreate()

}

/**
 * Finds the closest Activityin the context hierarchy.
 *
 * @return The Activity if found, null otherwise.
 */
fun Context.findActivity(): Activity? {
    val context = this
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
    }
    return null
}

fun getDefaultPhoneCode(context: Context): String {
    val defaultCountry = getDefaultLangCode(context)
    val defaultCode: CountryData =try {
        getLibCountries().first() { it.countryCode == defaultCountry }
    }
    catch(e:NoSuchElementException ){
        e.printStackTrace()
        getLibCountries()[12]
    }
    return defaultCode.countryPhoneCode.ifBlank { "+90" }
}

fun checkPhoneNumber(phone: String, fullPhoneNumber: String, countryCode: String): Boolean {
    val number: Phonenumber.PhoneNumber?
    if (phone.length > 6) {
        return try {
            number = PhoneNumberUtil.getInstance().parse(
                fullPhoneNumber,
                Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED.name
            )
            PhoneNumberUtil.getInstance().isValidNumberForRegion(number, countryCode.uppercase())
        } catch (ex: Exception) {
            false
        }
    }
    return false
}
