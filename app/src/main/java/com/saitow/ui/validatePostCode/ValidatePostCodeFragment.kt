package com.saitow.ui.validatePostCode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.saitow.R
import com.saitow.databinding.FragmentValidatePostCodeBinding
import com.saitow.ui.selectCountryDialog.SelectCountryFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ValidatePostCodeFragment : Fragment() {

    // for data binding
    private lateinit var binding: FragmentValidatePostCodeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_validate_post_code,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set select country click
        binding.selectCountry.setOnClickListener {
            openCountryDialog()
        }
    }

    private fun openCountryDialog(){
        val fm: FragmentManager = requireActivity().supportFragmentManager
        val selectCountryFragment = SelectCountryFragment()
        selectCountryFragment.show(fm, "fragment_select_country")
    }
}