package com.saitow.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.saitow.R
import com.saitow.data.model.BottomSheetItem
import com.saitow.databinding.ActivityMainBinding
import com.saitow.ui.bottomDrawer.BottomDrawerAdapter
import java.util.*


class MainActivity : AppCompatActivity() {

    // for navigation
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    // data binding for easy attack data with view
    lateinit var binding: ActivityMainBinding

    // init data for manipulate bottom sheet
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private var mAdapter: BottomDrawerAdapter? = null
    private val modelList: ArrayList<BottomSheetItem> = ArrayList<BottomSheetItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.bottomDrawer.toolbar)

        //set action bar with navController
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_search, R.id.nav_validate_bic, R.id.nav_validate_post_code), null)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // put options inside bottom drawer
        setBottomDrawerAdapter()

        //set bottom drawer behavior
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomDrawer.bottomNavigationContainer)
        // toggle bottom drawer when click on toolbar
        binding.bottomDrawer.toolbar.setOnClickListener {
            toggleBottomSheet()
        }

    }

    //region set bottom drawer options
    private fun setBottomDrawerAdapter() {
        modelList.add(BottomSheetItem("Search for Bank Data", R.drawable.ic_baseline_search_24))
        modelList.add(
            BottomSheetItem(
                "Validate BIC & IBAN",
                R.drawable.ic_baseline_monetization_on_24
            )
        )
        modelList.add(
            BottomSheetItem(
                "Validate PostCode",
                R.drawable.ic_baseline_monetization_on_24
            )
        )

        mAdapter = BottomDrawerAdapter(this, modelList)
        binding.bottomDrawer.drawer.setHasFixedSize(true)

        // use a linear layout manager
        val layoutManager = LinearLayoutManager(this)
        binding.bottomDrawer.drawer.layoutManager = layoutManager
        binding.bottomDrawer.drawer.adapter = mAdapter
        mAdapter!!.SetOnItemClickListener(object : BottomDrawerAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int, model: BottomSheetItem?) {
                when(position){
                    0->{
                        navController.navigate(R.id.nav_search)
                    }
                    1->{
                        navController.navigate(R.id.nav_validate_bic)
                    }
                    2->{
                        navController.navigate(R.id.nav_validate_post_code)
                    }
                }

            }

        })
    }

    private fun toggleBottomSheet() {
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
        }
    }
    // endregion
}