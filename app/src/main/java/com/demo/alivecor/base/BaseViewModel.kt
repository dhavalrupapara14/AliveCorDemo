package com.demo.alivecor.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Parent class for all ViewModel classes
 * Making it abstract as we don't allow initialization and there can be more abstract methods in future.
 */
abstract class BaseViewModel : ViewModel() {
    // Observable for view clicks
    val viewClick: MutableLiveData<Int> by lazy {
        MutableLiveData()
    }

    // Observable for showing validation errors on fields
    val showErrorFields: MutableLiveData<Int> by lazy {
        MutableLiveData()
    }

    // Observable for removing validation errors on fields
    val removeErrorFields: MutableLiveData<Int> by lazy {
        MutableLiveData()
    }
}