package com.demo.alivecor.ui.activity

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.demo.alivecor.R
import com.demo.alivecor.base.BaseActivity
import com.demo.alivecor.ui.fragment.ProfileFragment

/**
 * Main activity class which holds Profile and view profile fragments.
 */
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // replace profile fragment in the container.
        replaceFragment(
            ProfileFragment.newInstance(),
            false,
            ProfileFragment::class.java.simpleName
        )
    }

    /**
     * Method used to replace the fragment in this activity.
     * This gets called mostly from fragments.
     */
    override fun replaceFragment(fragment: Fragment, addToBackStack: Boolean, tag: String) {
        supportFragmentManager.commit {
            if (addToBackStack)
                addToBackStack(tag)

            replace(R.id.container, fragment, tag)
        }
    }

    /**
     * Method used to get support action bar if available.
     * This gets called mostly from fragments.
     */
    override fun getActivitySupportActionBar(): ActionBar? {
        return supportActionBar
    }
}