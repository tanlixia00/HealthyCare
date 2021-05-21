package com.example.projectuas_timxd_healthycare.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.projectuas_timxd_healthycare.model.User
import com.example.projectuas_timxd_healthycare.model.UserDatabase
import com.example.projectuas_timxd_healthycare.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FoodLogViewModel(application: Application):AndroidViewModel(application), CoroutineScope {
    val userLD = MutableLiveData<User>()
    private var job = Job()

    fun LoadUser(){
        launch {
            val db = buildDb(getApplication())
            userLD.value =db.userDao().selectAllUser()
        }
    }
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}