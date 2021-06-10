package com.example.projectuas_timxd_healthycare.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectuas_timxd_healthycare.R
import com.example.projectuas_timxd_healthycare.databinding.FragmentProfilBinding
import com.example.projectuas_timxd_healthycare.model.Global
import com.example.projectuas_timxd_healthycare.model.User
import com.example.projectuas_timxd_healthycare.viewmodel.DetailUserViewModel
import com.example.projectuas_timxd_healthycare.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.android.synthetic.main.fragment_profil.*

class ProfilFragment : Fragment(), AdapterView.OnItemSelectedListener, UpdateClickListener {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var dataBinding: FragmentProfilBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profil, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.LoadUser()
        observeViewModel()
        dataBinding.listener = this
        val adapterGender =
            ArrayAdapter(view.context, android.R.layout.simple_list_item_1, Global.gender)

        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapterGender
        spinner.onItemSelectedListener = this
    }

    fun observeViewModel() {
        viewModel.userLD.observe(viewLifecycleOwner, Observer {
            dataBinding.user = it
            if (it.gender == "Male") {
                dataBinding.spinner.setSelection(0)
            } else {
                dataBinding.spinner.setSelection(1)
            }
        })
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        dataBinding.user?.gender = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        dataBinding.user?.gender = "Male"
    }

    override fun onUpdateClick(v: View, obj: User) {
        viewModel.UpdateProfile(obj.name, obj.gender, obj.weight, obj.height, obj.age, obj.uuid)
        Toast.makeText(v.context, "Profile Updated", Toast.LENGTH_SHORT).show()
    }
}