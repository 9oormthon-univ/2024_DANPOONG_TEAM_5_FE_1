package com.project.jangburich.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentHomeGroupBottomSheetBinding
import com.project.jangburich.ui.MainActivity

interface HomeGroupBottomSheetListener {
    fun onButtonClicked(position: Int)
}
class HomeGroupBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var listener: HomeGroupBottomSheetListener
    lateinit var binding: FragmentHomeGroupBottomSheetBinding
    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = parentFragment as HomeGroupBottomSheetListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeGroupBottomSheetBinding.inflate(inflater, container, false)
        mainActivity = activity as MainActivity

        MyApplication.isFirst = false

        binding.run {
            buttonEnterGroup.setOnClickListener { onItemClicked(0) }
            buttonMakeGroup.setOnClickListener { onItemClicked(1) }
        }

        return binding.root
    }

    private fun onItemClicked(position: Int) {
        listener.onButtonClicked(position)

        dismiss()
    }


}