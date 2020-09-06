package com.saitow.ui.searchBankData

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saitow.R
import com.saitow.data.model.Bic
import com.saitow.databinding.FragmentSearchBankDataBinding
import com.saitow.utils.RecyclerViewScrollListener
import com.task.utils.Status
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class SearchBankDataFragment : Fragment() {

    private var mAdapter: SearchBankDataAdapter? = null
    private var scrollListener: RecyclerViewScrollListener? = null
    private val modelList = ArrayList<Bic>()

    //for data binding
    private lateinit var binding: FragmentSearchBankDataBinding

    //for view model
    private val searchBankDataViewModel : SearchBankDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_bank_data, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        hideSwipe()
        setupObserver()
        searchBankDataViewModel.doSearch("")
        setSearchView()
        setSearchClick()
    }

    private fun hideSwipe(){
        binding.swipeRefreshRecyclerList.isEnabled = false
        binding.swipeRefreshRecyclerList.isRefreshing = false
    }

    private fun showSwipe(){
        binding.swipeRefreshRecyclerList.isEnabled = true
        binding.swipeRefreshRecyclerList.isRefreshing = true
    }

    private fun setAdapter() {
        mAdapter = SearchBankDataAdapter(requireActivity(), modelList)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.isNestedScrollingEnabled = false

        // use a linear layout manager
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = mAdapter
        scrollListener = object : RecyclerViewScrollListener() {
            override fun onEndOfScrollReached(rv: RecyclerView?) {
                scrollListener!!.disableScrollListener()
            }
        }
        binding.recyclerView.addOnScrollListener(scrollListener!!)
        mAdapter!!.SetOnItemClickListener(object : SearchBankDataAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int, model: Bic?) {

                //handle item click events here
                Toast.makeText(activity, "Hey " + model!!.bankName, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setSearchView() {
        binding.searchView.setOnClickListener { v: View? -> binding.searchView.isIconified = false }
        binding.searchView.setQueryHint("Enter bank routing code ...")
        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length > 0) {
                    searchBankDataViewModel.doSearch(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length > 0) {
                    searchBankDataViewModel.doSearch(newText)
                }
                return true
            }
        })
    }

    private fun setSearchClick(){
        binding.searchButton.setOnClickListener {
            if (binding.searchView.query.isNotEmpty()){
                searchBankDataViewModel.doSearch(binding.searchView.query.toString())
            }
        }
    }

    // region observe data coming from server
    private fun setupObserver() {
        searchBankDataViewModel.response.observe(requireActivity(), {
            when (it.status) {
                Status.SUCCESS -> {
                    hideSwipe()
                    it.data?.let { data ->
                        mAdapter?.updateList(ArrayList(data.data.bics))
                    }
                    binding.recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    showSwipe()
                    binding.recyclerView.visibility = View.GONE
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