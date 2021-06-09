package com.example.projectuas_timxd_healthycare.view

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.projectuas_timxd_healthycare.R
import com.example.projectuas_timxd_healthycare.databinding.ReportItemLayoutBinding
import com.example.projectuas_timxd_healthycare.model.Food
import com.example.projectuas_timxd_healthycare.model.Report
import java.time.Instant
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class ReportListAdapter(val reportList: ArrayList<Report>) :
    RecyclerView.Adapter<ReportListAdapter.ReportViewHolder>() {
    class ReportViewHolder(var view: ReportItemLayoutBinding) : RecyclerView.ViewHolder(view.root)
    var tempList = arrayListOf<Report>()

    fun refreshList(list: List<Report>) {
        reportList.clear()
        val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("dd MMM yyyy")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        var idx = 0
        var instant = Instant.ofEpochMilli(0)
        var dates = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        if(list.isNotEmpty()){
            idx = list.lastIndex
            instant = Instant.ofEpochMilli(list[idx].date * 1000L)
            dates = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            var dayInMonth = YearMonth.of(dates.year, dates.month).lengthOfMonth()
            var c = Calendar.getInstance()
            for (x in 1..dayInMonth){
                var bool = false
                var listIdx = 0
                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), x, 0,0,0)
                var inMillis = c.timeInMillis / 1000L
                for (report in list){
                    if (report.date == inMillis){
                        bool = true
                    }else{
                        listIdx++
                    }
                }
                if (bool == true){
                    tempList.add(list[listIdx])
                }
                else{
                    val report = Report(inMillis,"LOW",0,0)
                    tempList.add(report)
                }
            }
            reportList.addAll(tempList)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ReportItemLayoutBinding>(
            inflater,
            R.layout.report_item_layout,
            parent,
            false
        )
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.view.report = reportList[position]
    }

    override fun getItemCount(): Int {
        return reportList.size
    }
}