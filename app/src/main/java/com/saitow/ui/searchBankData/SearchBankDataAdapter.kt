package com.saitow.ui.searchBankData

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.saitow.R
import com.saitow.data.model.Bic
import com.saitow.databinding.ItemBankDataListBinding
import java.util.*

/**
 * A custom adapter to use with the RecyclerView widget.
 */
class SearchBankDataAdapter(
    private val mContext: Context,
    private var modelList: ArrayList<Bic>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mItemClickListener: OnItemClickListener? = null
    fun updateList(modelList: ArrayList<Bic>) {
        this.modelList = modelList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemBankDataListBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), R.layout.item_bank_data_list, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        //Here you can fill your row view
        if (holder is ViewHolder) {
            val model = getItem(position)
            val genericViewHolder = holder
            holder.bind(model)
        }
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    fun SetOnItemClickListener(mItemClickListener: OnItemClickListener?) {
        this.mItemClickListener = mItemClickListener
    }

    private fun getItem(position: Int): Bic {
        return modelList[position]
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int, model: Bic?)
    }

    inner class ViewHolder(private val binding: ItemBankDataListBinding) : RecyclerView.ViewHolder(binding.root) {
        init {

            binding.root.setOnClickListener {
                mItemClickListener!!.onItemClick(
                    binding.root,
                    adapterPosition,
                    modelList[adapterPosition]
                )
            }
        }

        fun bind(model: Bic){
            binding.dataItem = model
            binding.executePendingBindings()
        }
    }
}