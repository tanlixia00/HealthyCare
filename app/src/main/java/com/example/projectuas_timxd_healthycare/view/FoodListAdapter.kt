package com.example.projectuas_timxd_healthycare.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.projectuas_timxd_healthycare.R
import com.example.projectuas_timxd_healthycare.databinding.FoodItemLayoutBinding
import com.example.projectuas_timxd_healthycare.model.Food
import java.util.*
import kotlin.collections.ArrayList

class FoodListAdapter(val foodList: ArrayList<Food>) :
    RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>() {
    class FoodViewHolder(var view: FoodItemLayoutBinding) : RecyclerView.ViewHolder(view.root)

    fun updatFoodList(newList: List<Food>) {
        foodList.clear()
        foodList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FoodItemLayoutBinding>(
            inflater,
            R.layout.food_item_layout,
            parent,
            false
        )
//        val view = inflater.inflate(R.layout.food_item_layout, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.view.food = foodList[position]
    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}