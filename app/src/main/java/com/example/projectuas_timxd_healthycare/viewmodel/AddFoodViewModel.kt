package com.example.projectuas_timxd_healthycare.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.projectuas_timxd_healthycare.model.Food
import com.example.projectuas_timxd_healthycare.model.User
import com.example.projectuas_timxd_healthycare.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AddFoodViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private var job = Job()
    val userLD = MutableLiveData<User>()

    fun addFood(list: List<Food>) {
        launch {
            val db = buildDb(getApplication())
            db.userDao().insertFood(*list.toTypedArray())
        }
    }

    fun getUser() {
        launch {
            val db = buildDb(getApplication())
            userLD.value = db.userDao().selectAllUser()
        }
    }

    fun updateUser(cal: Int, uuid: Int) {
        launch {
            val db = buildDb(getApplication())
            db.userDao().updateUserCal(cal, uuid)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}