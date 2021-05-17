package com.example.projectuas_timxd_healthycare.viewmodel

import android.app.Application
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.projectuas_timxd_healthycare.model.User
import com.example.projectuas_timxd_healthycare.model.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailUserViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private var job = Job()

    fun addUser(list: List<User>) {
        launch {
            val db = Room.databaseBuilder(
                getApplication(),
                UserDatabase::class.java, "newuserdb"
            ).build()
            db.userDao().insertAll(*list.toTypedArray())
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

}