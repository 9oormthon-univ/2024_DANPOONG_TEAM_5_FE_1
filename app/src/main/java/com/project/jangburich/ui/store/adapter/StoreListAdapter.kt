package com.project.jangburich.ui.store.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.jangburich.api.response.store.Store
import com.project.jangburich.databinding.RowStoreListBinding
import com.project.jangburich.ui.MainActivity

class StoreListAdapter (
    private var activity: MainActivity,
    private var stores: List<Store>
) :
    RecyclerView.Adapter<StoreListAdapter.ViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null
    private var selectedPosition: Int = 0

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    fun updateList(newStores: List<Store>) {
        stores = newStores
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            RowStoreListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.storeName.text = stores[position].name
        holder.storeCategory.text = stores[position].category
        if(stores[position].businessStatus == "open") {
            holder.isStoreOpen.text = "영업중"
        } else {
            holder.isStoreOpen.text = "영업 종료"
        }
        holder.storeTimeOpenClose.text = "${stores[position].closeTime} 영업 종료"
        holder.storePhoneNumber.text = stores[position].phoneNumber

        Glide.with(activity).load(stores[position].imageUrl).into(holder.storeImage)
    }

    override fun getItemCount() = stores.size

    inner class ViewHolder(val binding: RowStoreListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val storeName = binding.textViewStoreName
        val storeCategory = binding.textViewStoreCategory
        val isStoreOpen = binding.textViewTimeOpen
        val storeTimeOpenClose = binding.textViewTimeOpenClose
        val storePhoneNumber = binding.textViewPhonenumValue
        val storeImage = binding.imageViewStore

        init {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)

                // 클릭 리스너 호출
                onItemClickListener?.invoke(position)
            }
        }
    }
}