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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectuas_timxd_healthycare.R
import com.example.projectuas_timxd_healthycare.databinding.FragmentReportBinding
import com.example.projectuas_timxd_healthycare.model.Report
import com.example.projectuas_timxd_healthycare.viewmodel.ReportViewModel
import kotlinx.android.synthetic.main.fragment_report.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class ReportFragment : Fragment() {
    private lateinit var viewModel:ReportViewModel
    private lateinit var dataBinding:FragmentReportBinding
    private var reportAdapter = ReportListAdapter(arrayListOf())
    private var reportList = arrayListOf<Report>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_report, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recView.layoutManager = LinearLayoutManager(context)
        recView.adapter = reportAdapter
        viewModel = ViewModelProvider(this).get(ReportViewModel::class.java)
        viewModel.selectReport()
        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.reportLD.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                reportAdapter.refreshList(it)
                dataBinding.report = it[it.lastIndex]
            }
            else{
                val date = Calendar.getInstance().timeInMillis / 1000L
                val report = Report(date, "LOW", 0,0)
                reportAdapter.refreshList(arrayListOf())
                dataBinding.report = report
            }
        })
    }
}