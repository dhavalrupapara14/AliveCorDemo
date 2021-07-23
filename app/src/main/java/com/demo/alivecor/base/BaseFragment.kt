package com.demo.alivecor.base

import android.content.Context
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment

/**
 * Parent class for all fragment classes
 * Making it abstract as we don't allow initialization and there can be more abstract methods in future.
 */
abstract class BaseFragment : Fragment() {

    // Interface used by fragments to communicate with the base/host activity.
    private lateinit var baseActionListener: IBaseActionListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IBaseActionListener)
            baseActionListener = context
    }

    /**
     * Replace the fragment in the host activity
     */
    protected fun replaceFragment(fragment: Fragment, addToBackStack: Boolean, tag: String) {
        baseActionListener.replaceFragment(fragment, addToBackStack, tag)
    }

    /**
     * Get support action bar if available.
     */
    protected fun getActivitySupportActionBar(): ActionBar? {
        return baseActionListener.getActivitySupportActionBar()
    }

    /**
     * Show toast using given message and toast length.
     */
    protected fun showToast(message: String, length: Int) {
        baseActionListener.showToast(message, length)
    }
}