package com.demo.alivecor.viewmodel

import android.view.View
import android.widget.AdapterView
import com.demo.alivecor.R
import com.demo.alivecor.base.BaseViewModel
import com.demo.alivecor.utils.VIEW_DISABLE_TIME_VERY_SHORT
import com.demo.alivecor.utils.disableViewTemporary
import com.demo.alivecor.utils.isPastDate

/**
 * Shared view model between Profile and View profile fragments.
 * This is a use case of master/list and details screens.
 *
 * Both uses the same data, hence we can use activity's lifecycle scope and
 * preserve the same viewmodel instance across these 2 fragments.
 */
class SharedProfileViewModel : BaseViewModel() {

    var firstName: String? = null
    var lastName: String? = null
    // DOB day, month and year. arr[0] will be selected index of spinner and arr[1] will be selected value.
    val dobDay: IntArray = intArrayOf(0,0)
    val dobMonth: IntArray = intArrayOf(0,0)
    val dobYear: IntArray = intArrayOf(0,0)

    /**
     * Called when user taps on Next button.
     */
    fun onClick(view: View) {
        // disable view for few ms to avoid multiple taps.
        disableViewTemporary(view, VIEW_DISABLE_TIME_VERY_SHORT)
        var isGoodToGo = true

        // validate first name value
        if (firstName.isNullOrBlank()) {
            showErrorFields.value = R.id.tilFirstName
            isGoodToGo = false
        } else {
            removeErrorFields.value = R.id.tilFirstName
        }

        // validate last name value
        if (lastName.isNullOrBlank()) {
            showErrorFields.value = R.id.tilLastName
            isGoodToGo = false
        } else {
            removeErrorFields.value = R.id.tilLastName
        }

        // validate DOB value
        if (!isPastDate(dobDay[1], dobMonth[1], dobYear[1])) {
            showErrorFields.value = R.id.tvDobLabel
            isGoodToGo = false
        }

        // if it is good to go then set value to the observable live data.
        if (isGoodToGo)
            viewClick.value = view.id
    }

    /**
     * Called when any items is selected in the day dropdown (Spinner)
     */
    fun onDobDaySelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        dobDay[0] = pos
        dobDay[1] = parent.selectedItem as Int
    }

    /**
     * Called when any items is selected in the month dropdown (Spinner)
     */
    fun onDobMonthSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        dobMonth[0] = pos
        dobMonth[1] = pos + 1 // As months start from 1 and position starts from 0
    }

    /**
     * Called when any items is selected in the year dropdown (Spinner)
     */
    fun onDobYearSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        dobYear[0] = pos
        dobYear[1] = parent.selectedItem as Int
    }
}