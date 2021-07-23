package com.demo.alivecor.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.demo.alivecor.R
import com.demo.alivecor.base.BaseFragment
import com.demo.alivecor.databinding.FragmentProfileBinding
import com.demo.alivecor.utils.hideKeyboard
import com.demo.alivecor.viewmodel.SharedProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * Profile fragment where we user enters his/her profile details.
 */
class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: SharedProfileViewModel by activityViewModels()

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize viewmodel variable in the layout xml
        binding.viewModel = viewModel

        // Set action bar title and hide back button if shown.
        getActivitySupportActionBar()?.title = getString(R.string.user_profile)
        getActivitySupportActionBar()?.setDisplayHomeAsUpEnabled(false)

        // Initialize DOB day spinner
        setSpinner(spDobDay, (1..31).toList(), viewModel.dobDay[0])

        // Initialize DOB month spinner
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.months_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spDobMonth.adapter = adapter
            spDobMonth.setSelection(viewModel.dobMonth[0])
        }

        // Initialize DOB year spinner
        setSpinner(spDobYear, (2021 downTo 1950).toList(), viewModel.dobYear[0])

        // Set observers for listening event from user interaction.
        setObservers()

        // request focus on 1st text field.
        binding.etFirstName.requestFocus()
    }

    /**
     * Initializes spinner using given list of values.
     * Also initialize it with previously selected index value in case of orientation change.
     */
    private fun setSpinner(
        spinner: Spinner,
        list: List<Any>,
        selectedIndex: Int
    ) {
        ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            list
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter
            spinner.adapter = adapter
            spinner.setSelection(selectedIndex)
        }
    }

    private fun setObservers() {
        // Check to avoid setting observers multiple times
        if (viewModel.viewClick.hasActiveObservers())
            return

        // Next button click observer
        viewModel.viewClick.observe(this, Observer {
            if (it == binding.btnNext.id) {
                // reset value to avoid multiple callbacks
                viewModel.viewClick.value = -1

                // hide keyboard before moving to next fragment.
                hideKeyboard(requireActivity(), binding.etFirstName)

                // move to next fragment
                replaceFragment(
                    ViewProfileFragment.newInstance(),
                    true,
                    ViewProfileFragment::class.java.simpleName
                )
            }
        })

        // Show validation errors on fields
        viewModel.showErrorFields.observe(this, Observer {
            when(it) {
                R.id.tilFirstName -> {
                    binding.tilFirstName.error = getString(R.string.error_first_name)
                }
                R.id.tilLastName -> {
                    binding.tilLastName.error = getString(R.string.error_last_name)
                }
                R.id.tvDobLabel -> {
                    showToast(getString(R.string.error_dob_date), Toast.LENGTH_SHORT)
                }
            }
        })

        // Clear validation errors on fields.
        viewModel.removeErrorFields.observe(this, Observer {
            when(it) {
                R.id.tilFirstName -> {
                    binding.tilFirstName.error = null
                }
                R.id.tilLastName -> {
                    binding.tilLastName.error = null
                }
            }
        })
    }
}