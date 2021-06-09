package com.example.projectuas_timxd_healthycare.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.projectuas_timxd_healthycare.model.*
import com.example.projectuas_timxd_healthycare.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FoodLogViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    val userLD = MutableLiveData<User>()
    val foodLD = MutableLiveData<List<UserWithFoods>>()
    val reportLD = MutableLiveData<List<Report>>()
    private var job = Job()

    fun LoadUser() {
        launch {
            val db = buildDb(getApplication())
            userLD.value = db.userDao().selectAllUser()
        }
    }

    fun LoadFoodList() {
        launch {
            val db = buildDb(getApplication())
            foodLD.value = db.userDao().selectUserFoods()
        }
    }

    fun loadReportList(){
        launch {
            val db = buildDb(getApplication())
            reportLD.value = db.userDao().selectAllReport()
        }
    }

    fun resetSumCal(uuid: Int) {
        launch {
            val db = buildDb(getApplication())
            db.userDao().resetUserSumCal(uuid)
        }
    }

    fun updateStatus(status: String, idFood:Int) {
        launch {
            val db = buildDb(getApplication())
            db.userDao().updateStatus(status, idFood)
        }
    }

    fun insertReport(list:List<Report>){
        launch {
            val db = buildDb(getApplication())
            db.userDao().inserReport(*list.toTypedArray())

        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}