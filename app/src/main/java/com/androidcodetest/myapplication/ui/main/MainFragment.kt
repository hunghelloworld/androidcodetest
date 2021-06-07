package com.androidcodetest.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.androidcodetest.myapplication.R
import com.androidcodetest.myapplication.databinding.MainFragmentBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

     val viewModel: MainViewModel by    sharedViewModel<MainViewModel>()

    lateinit var viewDataBinding:MainFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
         viewDataBinding = DataBindingUtil.inflate<com.androidcodetest.myapplication.databinding.MainFragmentBinding>(
            inflater, R.layout.main_fragment, container, false
        )

        viewDataBinding.viewmodel = viewModel
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}