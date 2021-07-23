package com.demo.alivecor.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.*

/**
 * Utility file where we put all utility methods.
 */

/**
 * Disables view for given duration to avoid multiple taps by user
 */
fun disableViewTemporary(
    view: View?,
    millies: Long
) {
    try {
        if (view != null && view.isEnabled) {
            view.isEnabled = false
            Handler(Looper.getMainLooper()).postDelayed({
                //check null as view may became null if activity/fragment is cleared.
                view?.let {
                    it.isEnabled = true
                }
            }, millies)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * Hide keyboard programmatically
 */
fun hideKeyboard(
    activity: Activity,
    view: View
) {
    try {
        (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            view.windowToken,
            0
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * Show keyboard programmatically
 */
fun showKeyboard(
    activity: Activity
) {
    try {
        (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            0
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * Show text using HTML formatting
 */
fun fromHtml(text: String): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(text)
    }
}

/**
 * Check if the given day, month and year points to a past date or not
 */
fun isPastDate(
    day: Int,
    month: Int, //first month value is 1
    year: Int
): Boolean {
    val age: Triple<Int, Int, Int> = getAge(day, month, year)
    return age.first > 0 || age.second > 0 || age.third > 0
}

/**
 * Calculate age from given day, month and year.
 * Returns age as no. of years, no. of months and no. of days.
 *
 * @return Triple of years, months and days
 */
fun getAge(
    day: Int,
    month: Int, //first month value is 1
    year: Int
): Triple<Int, Int, Int> {

    var days = 0
    var months = 0
    var years = 0

    Calendar.getInstance().let {
        years = it.get(Calendar.YEAR) - year
        months = (it.get(Calendar.MONTH) + 1) - month // as first month starts with 0.
        days = it.get(Calendar.DAY_OF_MONTH) - day

        // when month is > current month
        if (months < 0) {
            years--
            months += 12 // it's actually 12 - month as we have -ve value of month
        }

        // when day is > current month
        if (days < 0) {
            months--
            days += 31 // it's actually 31 - days as we have -ve value of days
        }
    }

    return Triple(years, months, days)
}