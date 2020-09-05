package com.saitow.ui.searchBankData

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.saitow.R
import com.saitow.utils.RecyclerViewScrollListener
import com.task.utils.Status
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class SearchBankDataFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var swipeRefreshRecyclerList: SwipeRefreshLayout? = null
    private var mAdapter: SearchBankDataAdapter? = null
    private var scrollListener: RecyclerViewScrollListener? = null
    private val modelList = ArrayList<AbstractModel>()

    //for view model
    private val searchBankDataViewModel : SearchBankDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_bank_data, container, false)
        findViews(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        swipeRefreshRecyclerList!!.setOnRefreshListener { // Do your stuff on refresh
            Handler().postDelayed({
                if (swipeRefreshRecyclerList!!.isRefreshing) swipeRefreshRecyclerList!!.isRefreshing =
                    false
            }, 5000)
        }

        setupObserver()
        searchBankDataViewModel.doSearch("")
    }

    private fun findViews(view: View) {
        recyclerView = view.findViewById<View>(R.id.recycler_view) as RecyclerView
        swipeRefreshRecyclerList =
            view.findViewById<View>(R.id.swipe_refresh_recycler_list) as SwipeRefreshLayout
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)

        // Retrieve the SearchView and plug it into SearchManager
        val searchView = MenuItemCompat
            .getActionView(menu.findItem(R.id.action_search)) as SearchView
        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        //changing edittext color
        val searchEdit = searchView.findViewById<View>(R.id.search_src_text) as EditText
        searchEdit.setTextColor(Color.WHITE)
        searchEdit.setHintTextColor(Color.WHITE)
        searchEdit.setBackgroundColor(Color.TRANSPARENT)
        searchEdit.hint = "Search"
        val fArray = arrayOfNulls<InputFilter>(2)
        fArray[0] = LengthFilter(40)
        fArray[1] = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (!Character.isLetterOrDigit(source[i])) return@InputFilter ""
            }
            null
        }
        searchEdit.filters = fArray
        val v = searchView.findViewById<View>(R.id.search_plate)
        v.setBackgroundColor(Color.TRANSPARENT)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                val filterList = ArrayList<AbstractModel>()
                if (s.length > 0) {
                    for (i in modelList.indices) {
                        if (modelList[i].title.toLowerCase().contains(s.toLowerCase())) {
                            filterList.add(modelList[i])
                            mAdapter!!.updateList(filterList)
                        }
                    }
                } else {
                    mAdapter!!.updateList(modelList)
                }
                return false
            }
        })
    }

    private fun setAdapter() {
        modelList.add(AbstractModel("Android", "Hello " + " Android"))
        modelList.add(AbstractModel("Beta", "Hello " + " Beta"))
        modelList.add(AbstractModel("Cupcake", "Hello " + " Cupcake"))
        modelList.add(AbstractModel("Donut", "Hello " + " Donut"))
        modelList.add(AbstractModel("Eclair", "Hello " + " Eclair"))
        modelList.add(AbstractModel("Froyo", "Hello " + " Froyo"))
        modelList.add(AbstractModel("Gingerbread", "Hello " + " Gingerbread"))
        modelList.add(AbstractModel("Honeycomb", "Hello " + " Honeycomb"))
        modelList.add(AbstractModel("Ice Cream Sandwich", "Hello " + " Ice Cream Sandwich"))
        modelList.add(AbstractModel("Jelly Bean", "Hello " + " Jelly Bean"))
        modelList.add(AbstractModel("KitKat", "Hello " + " KitKat"))
        modelList.add(AbstractModel("Lollipop", "Hello " + " Lollipop"))
        modelList.add(AbstractModel("Marshmallow", "Hello " + " Marshmallow"))
        modelList.add(AbstractModel("Nougat", "Hello " + " Nougat"))
        modelList.add(AbstractModel("Android O", "Hello " + " Android O"))
        mAdapter = SearchBankDataAdapter(requireActivity(), modelList)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.isNestedScrollingEnabled = false

        // use a linear layout manager
        val layoutManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = mAdapter
        scrollListener = object : RecyclerViewScrollListener() {
            override fun onEndOfScrollReached(rv: RecyclerView?) {
                Toast.makeText(
                    activity,
                    "End of the RecyclerView reached. Do your pagination stuff here",
                    Toast.LENGTH_SHORT
                ).show()
                scrollListener!!.disableScrollListener()
            }
        }
        recyclerView!!.addOnScrollListener(scrollListener!!)
        /*
             Note: The below two methods should be used wisely to handle the pagination enable and disable states based on the use case.
                     1. scrollListener.disableScrollListener(); - Should be called to disable the scroll state.
                     2. scrollListener.enableScrollListener(); - Should be called to enable the scroll state.
          */mAdapter!!.SetOnItemClickListener(object : SearchBankDataAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int, model: AbstractModel?) {

                //handle item click events here
                Toast.makeText(activity, "Hey " + model!!.title, Toast.LENGTH_SHORT).show()
            }
        })
    }

    // region observe data coming from server
    private fun setupObserver() {
        searchBankDataViewModel.response.observe(requireActivity(), {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireActivity(), it.data?.code, Toast.LENGTH_LONG).show()
//                    progressBar.visibility = View.GONE
//                    it.data?.let { users -> renderList(users) }
//                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
//                    progressBar.visibility = View.VISIBLE
//                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //progressBar.visibility = View.GONE
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
    //endregion
}