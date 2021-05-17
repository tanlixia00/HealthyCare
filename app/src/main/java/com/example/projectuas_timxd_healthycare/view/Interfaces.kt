package com.example.projectuas_timxd_healthycare.view

import android.view.View
import com.example.projectuas_timxd_healthycare.model.User

interface RadioClickListener{
    fun onRadioClick(v:View, obj:User)
}
interface SpinnerClickListener{
    fun onSpinnerClick(v:View, obj:User)
}
interface StartClickListener{
    fun onStartClick(v:View, obj:User)
}