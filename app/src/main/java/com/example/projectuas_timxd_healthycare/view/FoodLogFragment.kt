package com.example.projectuas_timxd_healthycare.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectuas_timxd_healthycare.R
import com.example.projectuas_timxd_healthycare.databinding.FragmentFoodLogBinding
import com.example.projectuas_timxd_healthycare.model.Food
import com.example.projectuas_timxd_healthycare.model.Report
import com.example.projectuas_timxd_healthycare.viewmodel.FoodLogViewModel
import kotlinx.android.synthetic.main.fragment_food_log.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class FoodLogFragment : Fragment(), FABClickListener {
    private lateinit var viewModel: FoodLogViewModel
    private lateinit var dataBinding: FragmentFoodLogBinding
    private var foodLogAdapter = FoodListAdapter(arrayListOf())
    private var reportList = arrayListOf<Report>()
    var foods = arrayListOf<Food>()
    var reportFoods = arrayListOf<Food>()

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
        recViewFood.layoutManager = LinearLayoutManager(context)
        recViewFood.adapter = foodLogAdapter
        viewModel = ViewModelProvider(this).get(FoodLogViewModel::class.java)
        viewModel.LoadUser()
        viewModel.loadReportList()
        viewModel.LoadFoodList()

        dataBinding.fabClickListener = this
        observeViewModel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            txtDate.text =
                LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
        }
    }

    fun observeViewModel() {
        viewModel.reportLD.observe(viewLifecycleOwner, Observer {
            reportList.clear()
            reportList.addAll(it)
            viewModel.foodLD.observe(viewLifecycleOwner, Observer {
                foods.clear()
                reportFoods.clear()
                var lastDate = 0L
                var sumMeal = 0
                var sumCal = 0
                var now = Calendar.getInstance()
                var yesterday = Calendar.getInstance()
                now.set(
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH),
                    0,
                    0,
                    0
                )
                var nowInMilis = now.timeInMillis / 1000L
                if (it[0].foods.size > 0) {
                    lastDate = it[0].foods[it[0].foods.lastIndex].date
                }
                if (lastDate == nowInMilis) {
                    for (food in it[0].foods) {
                        if (food.date == nowInMilis) {
                            foods.add(food)
                        }
                        lastDate = food.date
                    }
                }
                if (lastDate < nowInMilis) {
                    yesterday.set(
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        (now.get(Calendar.DAY_OF_MONTH) - 1),
                        0,
                        0,
                        0
                    )
                    Log.d("tanggal", yesterday.get(Calendar.DAY_OF_MONTH).toString())
                    var yesterdayInMilis = yesterday.timeInMillis / 1000L
                    if (lastDate == yesterdayInMilis) {
                        for (food in it[0].foods) {
                            if (food.date == yesterdayInMilis) {
                                reportFoods.add(food)
                                sumMeal++
                                sumCal += food.cal.toInt()
                                lastDate = food.date
                            }
                        }
                        if (reportList.isNullOrEmpty()) {
                            viewModel.insertReport(
                                listOf(
                                    Report(
                                        lastDate,
                                        it[0].user.status,
                                        sumMeal,
                                        sumCal
                                    )
                                )
                            )
                            viewModel.resetSumCal(it[0].user.uuid)
                        } else {
                            var reportDate:Long = 0
                            for (report in reportList) {
                                if (report.date != yesterdayInMilis) {
                                    reportDate = report.date
                                }
                                else{
                                    reportDate = yesterdayInMilis
                                }
                            }
                            if (reportDate != yesterdayInMilis) {
                                viewModel.insertReport(
                                    listOf(
                                        Report(
                                            lastDate,
                                            it[0].user.status,
                                            sumMeal,
                                            sumCal
                                        )
                                    )
                                )
                                viewModel.resetSumCal(it[0].user.uuid)
                            }
                        }
                    }
                }
                foodLogAdapter.updatFoodList(foods)
                viewModel.LoadUser()
            })
        })

        viewModel.userLD.observe(viewLifecycleOwner, Observer
        {
            if (it.sumCal < it.maxCal) {
                if (it.sumCal <= (it.maxCal * 0.5)) {
                    it.status = "LOW"
                } else if (it.sumCal > (it.maxCal * 0.5)) {
                    it.status = "NORMAL"
                }
            } else {
                it.status = "EXCEED"
            }
            viewModel.updateStatus(it.status, it.uuid)
            dataBinding.user = it
        })
    }

    override fun onFABClick(v: View) {
        val action = FoodLogFragmentDirections.actionLogAMeal(v.tag.toString().toInt())
        Navigation.findNavController(v).navigate(action)
    }
}