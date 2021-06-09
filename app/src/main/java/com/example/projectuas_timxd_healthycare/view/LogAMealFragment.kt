package com.example.projectuas_timxd_healthycare.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.projectuas_timxd_healthycare.R
import com.example.projectuas_timxd_healthycare.databinding.FragmentLogAMealBinding
import com.example.projectuas_timxd_healthycare.model.Food
import com.example.projectuas_timxd_healthycare.util.hideKeyboard
import com.example.projectuas_timxd_healthycare.viewmodel.AddFoodViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_log_a_meal.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

class LogAMealFragment : Fragment(), LogClickListener {
    lateinit var viewModel: AddFoodViewModel
    lateinit var dataBinding: FragmentLogAMealBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_log_a_meal, container, false)
        // Inflate the layout for this fragment
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddFoodViewModel::class.java)
        viewModel.getUser()
        dataBinding.logClickListener = this
        dataBinding.food = Food("", "", 0, 0)
        observeViewModel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            txtTanggal.text =
                LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
        }
    }

    fun observeViewModel() {
        viewModel.userLD.observe(viewLifecycleOwner, Observer {
            if (it.maxCal - it.sumCal >= 0) {
                txtCalNeeded.text = "${it.maxCal - it.sumCal} Cal"
            } else {
                txtCalNeeded.text = "0"
            }
        })
    }

    override fun onLogClick(v: View) {
        hideKeyboard(v)
        val uuid = LogAMealFragmentArgs.fromBundle(requireArguments()).uuid
        dataBinding.food!!.userId = uuid
        var names = dataBinding.food!!.name.split(' ')
        var count = 0
        var capName = ""
        for (name in names) {
            if (count == 0) {
                capName += name.capitalize()
                count++
            } else {
                capName += " "
                capName += name.capitalize()
                count++
            }
        }
        dataBinding.food!!.name = capName
        var rightNow = Calendar.getInstance()
        rightNow.set(
            rightNow.get(Calendar.YEAR),
            rightNow.get(Calendar.MONTH),
            rightNow.get(Calendar.DAY_OF_MONTH),
            0,
            0,
            0
        )
        dataBinding.food!!.date = rightNow.timeInMillis / 1000L
        val list = listOf(dataBinding.food!!)
        viewModel.addFood(list)
        viewModel.updateUser(dataBinding.food!!.cal.toString().toInt(), uuid.toInt())
//        Toast.makeText(v.context, "Adding Food", Toast.LENGTH_SHORT).show()
        Toast.makeText(v.context, "Food Added", Toast.LENGTH_LONG).show()
        viewModel.getUser()
        observeViewModel()
        Navigation.findNavController(v).popBackStack()

    }
}