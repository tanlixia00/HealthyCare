package com.example.projectuas_timxd_healthycare.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.projectuas_timxd_healthycare.R
import com.example.projectuas_timxd_healthycare.databinding.ActivityWelcomeBinding
import com.example.projectuas_timxd_healthycare.model.Global
import com.example.projectuas_timxd_healthycare.model.User
import com.example.projectuas_timxd_healthycare.viewmodel.DetailUserViewModel
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlin.math.log
import kotlin.math.roundToInt

class WelcomeActivity : AppCompatActivity(), RadioClickListener, SpinnerClickListener,
    StartClickListener, AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_welcome)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)
        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        val adapterGender = ArrayAdapter(this, android.R.layout.simple_list_item_1, Global.gender)

        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapterGender
        spinnerGender.onItemSelectedListener = this
        binding.radioListener = this
        binding.spinnerListener = this
        binding.startListener = this
        binding.user = User("", "", "", "", "", "", 0, "0")

        val pref = this.getSharedPreferences("PREFS", 0)
        val boolPref = pref.getBoolean("first", true)
        if (boolPref == false) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onRadioClick(v: View, obj: User) {
        obj.goal = v.tag.toString()
    }

    override fun onSpinnerClick(v: View, obj: User) {
        var spinner = v as Spinner
        obj.gender = spinner.selectedItem.toString()
    }

    override fun onStartClick(v: View, obj: User) {
        val list = listOf(obj)
        viewModel.addUser(list)
        Toast.makeText(v.context, "Data added", Toast.LENGTH_LONG).show()
        val pref = this.getSharedPreferences("PREFS", 0)
        val editor = pref.edit()
        editor.putBoolean("first", false).apply()
        if (obj.gender == "Male") {
            obj.bmr =
                ((13.397 * obj.weight.toInt()) + (4.799 * obj.height.toInt()) - (5.677 * obj.age.toInt()) + 88.362).roundToInt()
        } else {
            obj.bmr =
                ((9.247 * obj.weight.toInt()) + (3.098 * obj.height.toInt()) - (4.330 * obj.age.toInt()) + 447.593).roundToInt()
        }
        if (obj.goal == "maintain") {
            obj.maxCal = obj.bmr.toString()
        } else if (obj.goal == "gain") {
            obj.maxCal = (obj.bmr + (obj.bmr * 0.15).roundToInt()).toString()
        } else {
            obj.maxCal = (obj.bmr - (obj.bmr * 0.15).roundToInt()).toString()
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        binding.user?.gender = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        binding.user?.gender = "Male"
    }
}