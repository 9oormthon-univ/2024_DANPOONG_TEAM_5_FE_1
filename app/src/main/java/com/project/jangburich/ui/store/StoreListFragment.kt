package com.project.jangburich.ui.store

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.api.response.store.Store
import com.project.jangburich.databinding.FragmentStoreListBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.home.adapter.TeamAdapter
import com.project.jangburich.ui.login.LoginMainFragment
import com.project.jangburich.ui.store.adapter.StoreListAdapter
import com.project.jangburich.ui.store.viewModel.StoreViewModel

class StoreListFragment : Fragment() {

    lateinit var binding: FragmentStoreListBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: StoreViewModel

    var getStoreList = mutableListOf<Store>()

    private lateinit var storeAdapter: StoreListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoreListBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[StoreViewModel::class.java]

        initAdapters()

        viewModel.run {
            storeList.observe(mainActivity) {
                getStoreList = it
                storeAdapter.updateList(getStoreList)
                Log.d("##", "store list : ${getStoreList}")
            }
        }

        mainActivity.hideBottomNavigation(false)

        binding.run {
            buttonMap.setOnClickListener {
                val nextFragment = StoreMapFragment()

                val transaction = mainActivity.manager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                transaction.addToBackStack("")
                transaction.commit()
            }
        }

        return binding.root
    }

    private fun initAdapters() {
        // 어댑터 초기화
        storeAdapter = StoreListAdapter(
            mainActivity,
            getStoreList
        ).apply {
            itemClickListener = object : StoreListAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    MyApplication.storeId = getStoreList[position].storeId.toLong()
                    MyApplication.storeName = getStoreList[position].name
                    MyApplication.storeCategory = getStoreList[position].category
                    MyApplication.storeImage = getStoreList[position].imageUrl.toString()

                    val nextFragment = StoreDetailFragment()

                    val transaction = mainActivity.manager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                    transaction.addToBackStack("")
                    transaction.commit()
                }
            }
        }

        binding.recyclerViewStoreList.apply {
            adapter = storeAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }


}