package com.saitow.ui.validateBICAndIBAN

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.saitow.R
import com.saitow.data.model.ValidationResponse
import com.saitow.databinding.FragmentValidateBicAndIbanBinding
import com.task.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ValidateBICAndIBANFragment : Fragment() {

    // for dataBinding
    private lateinit var binding: FragmentValidateBicAndIbanBinding

    //for view model
    private val validateBICAndIBANViewModel : ValidateBICAndIBANViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_validate_bic_and_iban, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set titles
        binding.dataItemIban = ValidationResponse()
        binding.dataItemBic = ValidationResponse()
        binding.executePendingBindings()
        binding.validateBicTitle.text = "VALIDATE BIC"
        binding.validateIbanTitle.text = "VALIDATE IBAN"
        binding.validateBicTitle.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorAccent))
        binding.validateIbanTitle.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorAccent))


        // listen for different actions
        hideSwipe()
        setupBicObserver()
        setupIbanObserver()
        setValidateBICClick()
        setValidateIbanClick()
        setSearchViewBic()
        setSearchViewIban()


    }

    private fun hideSwipe(){
        binding.swipeRefreshRecyclerList.isEnabled = false
        binding.swipeRefreshRecyclerList.isRefreshing = false
    }

    private fun showSwipe(){
        binding.swipeRefreshRecyclerList.isEnabled = true
        binding.swipeRefreshRecyclerList.isRefreshing = true
    }

    private fun setSearchViewBic() {
        binding.searchViewBic.setOnClickListener { v: View? -> binding.searchViewBic.isIconified = false }
        binding.searchViewBic.setQueryHint("Enter BIC ...")
        binding.searchViewBic.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length > 0) {
                    validateBICAndIBANViewModel.validateBIC(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length > 0) {
                    validateBICAndIBANViewModel.validateBIC(newText)
                }
                return true
            }
        })
    }

    private fun setSearchViewIban() {
        binding.searchViewIban.setOnClickListener { v: View? -> binding.searchViewIban.isIconified = false }
        binding.searchViewIban.setQueryHint("Enter IBAN ...")
        binding.searchViewIban.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length > 0) {
                    validateBICAndIBANViewModel.validateIBAN(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length > 0) {

                }
                return true
            }
        })
    }

    private fun setValidateBICClick(){
        binding.validateBicButton.setOnClickListener {
            if (binding.searchViewBic.query.isNotEmpty()){
                validateBICAndIBANViewModel.validateBIC(binding.searchViewBic.query.toString())
            }
        }
    }

    private fun setValidateIbanClick(){
        binding.validateIbanButton.setOnClickListener {
            if (binding.searchViewIban.query.isNotEmpty()){
                validateBICAndIBANViewModel.validateIBAN(binding.searchViewIban.query.toString())
            }
        }
    }

    // region observe data coming from server
    private fun setupBicObserver() {
        validateBICAndIBANViewModel.responseBIC.observe(requireActivity(), {
            when (it.status) {
                Status.SUCCESS -> {
                    hideSwipe()
                    it.data?.let { data ->
                        binding.dataItemBic = data
                        binding.executePendingBindings()
                        binding.validateIbanTitle.text = "VALIDATE IBAN"
                        binding.validateIbanTitle.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorAccent))
                    }
                }
                Status.LOADING -> {
                    showSwipe()
                }
                Status.ERROR -> {
                    hideSwipe()
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setupIbanObserver() {
        validateBICAndIBANViewModel.responseIBAN.observe(requireActivity(), {
            when (it.status) {
                Status.SUCCESS -> {
                    hideSwipe()
                    it.data?.let { data ->
                        binding.dataItemIban = data
                        binding.executePendingBindings()
                        binding.validateBicTitle.text = "VALIDATE BIC"
                        binding.validateBicTitle.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorAccent))
                    }
                }
                Status.LOADING -> {
                    showSwipe()
                }
                Status.ERROR -> {
                    hideSwipe()
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
    //endregion
}