package com.example.projectuas_timxd_healthycare.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.projectuas_timxd_healthycare.model.User
import com.example.projectuas_timxd_healthycare.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ProfileViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    val userLD = MutableLiveData<User>()
    private var job = Job()

    fun LoadUser() {
        launch {
            val db = buildDb(getApplication())
            userLD.value = db.userDao().selectAllUser()
        }
    }

    fun UpdateProfile(
        name: String,
        gender: String,
        weight: String,
        height: String,
        age: String,
        uuid: Int
    ) {
        launch {
            val db = buildDb(getApplication())
            db.userDao().updateProfile(name, gender, weight, height, age, uuid)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}