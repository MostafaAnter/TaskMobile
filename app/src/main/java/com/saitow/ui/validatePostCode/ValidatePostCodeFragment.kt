package com.saitow.ui.validatePostCode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.saitow.R
import com.saitow.data.model.Country
import com.saitow.data.model.ValidationResponse
import com.saitow.databinding.FragmentValidatePostCodeBinding
import com.saitow.ui.selectCountryDialog.SelectCountryFragment
import com.task.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@AndroidEntryPoint
class ValidatePostCodeFragment : Fragment() {
    // for selected country
    private var isCountrySelected = false
    private var countryCode = ""

    // for data binding
    private lateinit var binding: FragmentValidatePostCodeBinding

    //for view model
    private val validatePostCodeViewModel : ValidatePostCodeViewModel by viewModels()

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

        //set titles
        binding.dataItem = ValidationResponse()
        binding.executePendingBindings()
        binding.validatePostCodeTitle.text = "VALIDATE POST CODE"
        binding.validatePostCodeTitle.setTextColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.colorAccent
            )
        )

        // listen for different actions
        hideSwipe()
        setupObserver()
        setValidateButtonClick()
        setSearchView()
    }

    private fun openCountryDialog(){
        val fm: FragmentManager = requireActivity().supportFragmentManager
        val selectCountryFragment = SelectCountryFragment()
        selectCountryFragment.show(fm, "fragment_select_country")
    }

    private fun hideSwipe(){
        binding.swipeRefreshRecyclerList.isEnabled = false
        binding.swipeRefreshRecyclerList.isRefreshing = false
    }

    private fun showSwipe(){
        binding.swipeRefreshRecyclerList.isEnabled = true
        binding.swipeRefreshRecyclerList.isRefreshing = true
    }

    private fun setSearchView() {
        binding.searchView.setOnClickListener { v: View? -> binding.searchView.isIconified = false }
        binding.searchView.setQueryHint("Enter Post Code ...")
        binding.searchView.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length > 0 && isCountrySelected) {
                    validatePostCodeViewModel.validatePostCode(countryCode, query)
                } else {
                    Toast.makeText(requireActivity(), "Select Country", Toast.LENGTH_SHORT).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length > 0 && isCountrySelected) {
                    validatePostCodeViewModel.validatePostCode(countryCode, newText)
                }
                return true
            }
        })
    }

    private fun setValidateButtonClick(){
        binding.validatePostCodeButton.setOnClickListener {
            if (binding.searchView.query.isNotEmpty() && isCountrySelected){
                validatePostCodeViewModel.validatePostCode(
                    countryCode,
                    binding.searchView.query.toString()
                )
            }else{
                Toast.makeText(requireActivity(), "Select Country", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // region observe data coming from server
    private fun setupObserver() {
        validatePostCodeViewModel.responsePostCode.observe(requireActivity(), {
            when (it.status) {
                Status.SUCCESS -> {
                    hideSwipe()
                    it.data?.let { data ->
                        binding.dataItem = data
                        binding.executePendingBindings()
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

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Country) { /* Do something */
        isCountrySelected = true
        countryCode = event.code
        binding.selectCountry.text = event.name
    }

}