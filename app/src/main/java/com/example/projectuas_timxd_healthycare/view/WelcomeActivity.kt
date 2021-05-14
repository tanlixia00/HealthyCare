package com.example.projectuas_timxd_healthycare.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.projectuas_timxd_healthycare.R
import com.example.projectuas_timxd_healthycare.model.Global
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val adapterGender = ArrayAdapter(this, android.R.layout.simple_list_item_1, Global.gender)

        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapterGender
    }
}