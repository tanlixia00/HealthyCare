package com.example.projectuas_timxd_healthycare.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectuas_timxd_healthycare.R
import com.example.projectuas_timxd_healthycare.databinding.FragmentFoodLogBinding
import com.example.projectuas_timxd_healthycare.viewmodel.FoodLogViewModel
import kotlinx.android.synthetic.main.fragment_food_log.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class FoodLogFragment : Fragment() {
    private lateinit var viewModel: FoodLogViewModel
    private lateinit var dataBinding: FragmentFoodLogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate<FragmentFoodLogBinding>(
            inflater,
            R.layout.fragment_food_log,
            container,
            false
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FoodLogViewModel::class.java)
        viewModel.LoadUser()
        observeViewModel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            txtDate.text = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
        }
    }

    fun observeViewModel() {
        viewModel.userLD.observe(viewLifecycleOwner, Observer {
            dataBinding.user = it
        })
    }
}