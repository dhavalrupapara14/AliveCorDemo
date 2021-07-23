package com.demo.alivecor.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Parent class for all activity classes.
 * Making it abstract as we don't allow initialization and there can be more abstract methods in future.
 */
abstract class BaseActivity : AppCompatActivity(), IBaseActionListener {

    /**
     * Show toast using given message and toast length.
     * This gets called mostly from fragments.
     */
    override fun showToast(message: String, length: Int) {
        Toast.makeText(this, message, length).show()
    }
}