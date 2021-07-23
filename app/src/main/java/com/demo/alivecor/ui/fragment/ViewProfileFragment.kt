package com.demo.alivecor.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.demo.alivecor.R
import com.demo.alivecor.base.BaseFragment
import com.demo.alivecor.databinding.FragmentViewProfileBinding
import com.demo.alivecor.utils.fromHtml
import com.demo.alivecor.utils.getAge
import com.demo.alivecor.viewmodel.SharedProfileViewModel

/**
 * View Profile details fragment where we show details entered in the previous screen.
 */
class ViewProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentViewProfileBinding
    private val viewModel: SharedProfileViewModel by activityViewModels()

    companion object {
        fun newInstance() = ViewProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set action bar title and show back button.
        getActivitySupportActionBar()?.title = getString(R.string.profile_details)
        getActivitySupportActionBar()?.setDisplayHomeAsUpEnabled(true)


        // Calculate age from inputs given in previous screen and show it in the Text view.
        val age: Triple<Int, Int, Int> = getAge(
            viewModel.dobDay[1],
            viewModel.dobMonth[1],
            viewModel.dobYear[1]
        )
        binding.tvProfileDetails.text = fromHtml(
            getString(
                R.string.profile_detail_values,
                viewModel.firstName?.trim(),
                viewModel.lastName?.trim(),
                resources.getQuantityString(R.plurals.years, age.first, age.first),
                resources.getQuantityString(R.plurals.months, age.second, age.second),
                resources.getQuantityString(R.plurals.days, age.third, age.third)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Required to handle back press of action bar from fragment
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}