package com.saitow.ui.selectCountryDialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.saitow.R
import com.saitow.data.model.Country
import com.saitow.databinding.ItemCountryBinding
import java.util.*

/**
 * A custom adapter to use with the RecyclerView widget.
 */
class SelectCountryAdapter(
    private val mContext: Context,
    private var modelList: ArrayList<Country>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mItemClickListener: OnItemClickListener? = null

    fun updateList(modelList: ArrayList<Country>) {
        this.modelList = modelList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCountryBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), R.layout.item_country, viewGroup, false)
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

    private fun getItem(position: Int): Country {
        return modelList[position]
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int, model: Country?)
    }

    inner class ViewHolder(private val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                mItemClickListener!!.onItemClick(
                    binding.root,
                    adapterPosition,
                    modelList[adapterPosition]
                )
            }
        }

        fun bind(model: Country){
            binding.dataItem = model
            binding.executePendingBindings()
        }
    }
}