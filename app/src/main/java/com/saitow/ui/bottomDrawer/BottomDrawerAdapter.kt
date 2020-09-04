package com.saitow.ui.bottomDrawer

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.saitow.R
import com.saitow.data.model.BottomSheetItem
import com.saitow.databinding.ItemBottomSheetRecyclerListBinding
import java.util.*


/**
 * A custom adapter to use with the RecyclerView inside bottom sheet widget.
 */
class BottomDrawerAdapter(
    private val mContext: Context,
    private var modelList: ArrayList<BottomSheetItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mItemClickListener: OnItemClickListener? = null

    fun updateList(modelList: ArrayList<BottomSheetItem>) {
        this.modelList = modelList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemBottomSheetRecyclerListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(
                viewGroup.context
            ), R.layout.item__bottom_sheet_recycler_list,
            viewGroup, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        //Here you can fill your row view
        if (holder is ViewHolder) {
            val model = getItem(position)
            holder.bind(model)
        }
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    fun SetOnItemClickListener(mItemClickListener: OnItemClickListener?) {
        this.mItemClickListener = mItemClickListener
    }

    private fun getItem(position: Int): BottomSheetItem {
        return modelList[position]
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int, model: BottomSheetItem?)
    }

    inner class ViewHolder(private val binding: ItemBottomSheetRecyclerListBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        init {
            binding.root.setOnClickListener {
                mItemClickListener!!.onItemClick(
                    binding.root,
                    adapterPosition,
                    modelList[adapterPosition]
                )
            }
        }

        fun bind(model: BottomSheetItem){
            binding.dataItem = model
            binding.executePendingBindings()
        }
    }
}