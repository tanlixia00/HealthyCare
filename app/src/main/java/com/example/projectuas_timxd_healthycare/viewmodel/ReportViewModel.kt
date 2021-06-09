package com.example.projectuas_timxd_healthycare.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.projectuas_timxd_healthycare.model.Report
import com.example.projectuas_timxd_healthycare.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ReportViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    val reportLD = MutableLiveData<List<Report>>()
    private var job = Job()

    fun selectReport() {
        launch {
            val db = buildDb(getApplication())
            reportLD.value = db.userDao().selectAllReport()
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}