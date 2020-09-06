package com.saitow.ui.selectCountryDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.saitow.R
import com.saitow.data.model.Country
import com.saitow.databinding.DialogCountryBinding
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.nio.charset.Charset


/**
 * A simple [Fragment] subclass.
 */
class SelectCountryFragment : DialogFragment() {
    private var mAdapter: SelectCountryAdapter? = null
    private val modelList: ArrayList<Country> = ArrayList()

    private lateinit var binding: DialogCountryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_country, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setTitle("Select Country:")
        setAdapter()
        setSearchView()
    }

    override fun onStart() {
        super.onStart()
        // safety check
        if (getDialog() == null)
            return;

        val dialogWidth = activity?.getResources()?.getDisplayMetrics()?.widthPixels ?: 1000
        val dialogHeight = activity?.getResources()?.getDisplayMetrics()?.heightPixels ?: 1000

        getDialog()?.getWindow()?.setLayout(dialogWidth, dialogWidth)
    }

    private fun setAdapter() {
        modelList.addAll(ArrayList(getAllCountries(loadJSONFromAsset())))
        mAdapter = SelectCountryAdapter(requireActivity(), modelList)
        binding.recyclerView.setHasFixedSize(true)

        // use a linear layout manager
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = mAdapter
        mAdapter!!.SetOnItemClickListener(object : SelectCountryAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int, model: Country?) {
                EventBus.getDefault().post(model)
                dismiss()
            }

        })
    }

    //region get countries
    private fun setSearchView() {
        binding.searchView.setOnClickListener { v: View? -> binding.searchView.isIconified = false }
        binding.searchView.setQueryHint("Enter Country name ...")
        binding.searchView.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val filterList: ArrayList<Country> = ArrayList<Country>()
                if (query.length > 0) {
                    for (i in modelList.indices) {
                        if (modelList[i].name.toLowerCase().contains(query.toLowerCase())) {
                            filterList.add(modelList[i])
                            mAdapter!!.updateList(filterList)
                        }
                    }
                } else {
                    mAdapter!!.updateList(modelList)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val filterList: ArrayList<Country> = ArrayList<Country>()
                if (newText.length > 0) {
                    for (i in modelList.indices) {
                        if (modelList[i].name.toLowerCase().contains(newText.toLowerCase())) {
                            filterList.add(modelList[i])
                            mAdapter!!.updateList(filterList)
                        }
                    }
                } else {
                    mAdapter!!.updateList(modelList)
                }
                return true
            }
        })
    }

    fun loadJSONFromAsset(): String? {
        var json: String? = null
        json = try {
            val `is` = requireActivity().assets.open("json.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charset.forName("utf-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }


    private fun getAllCountries(json: String?): MutableList<Country> {
        val result: MutableList<Country> = ArrayList()
        try {
            val arr = JSONArray(json)
            for (i in 0 until arr.length()) {
                val countryItem = arr.getJSONObject(i)
                val item = Country(
                    countryItem.getString("name"),
                    countryItem.getString("dial_code"),
                    countryItem.getString("code"),
                    countryItem.getString("coor")
                )
                result.add(item)
            }
        } catch (e: JSONException) {
            return result
        }
        return result
    }

    //endregion
}