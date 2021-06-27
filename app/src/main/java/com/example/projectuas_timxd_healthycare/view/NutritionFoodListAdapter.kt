package com.example.projectuas_timxd_healthycare.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectuas_timxd_healthycare.R
import com.example.projectuas_timxd_healthycare.model.Nutrition
import kotlinx.android.synthetic.main.nutrition_item_layout.view.*

class NutritionFoodListAdapter(var foods: ArrayList<Nutrition>) :
    RecyclerView.Adapter<NutritionFoodListAdapter.NutritionViewHolder>(){

    class NutritionViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NutritionFoodListAdapter.NutritionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.nutrition_item_layout, parent, false)
        return NutritionFoodListAdapter.NutritionViewHolder(view)
    }
    override fun onBindViewHolder(holder: NutritionViewHolder, position: Int) {

        var food = foods[position]
        with(holder.view) {
            txtNutFoodName.text = food.name.toString()
            txtNutFoodFiber.text = food.fiber.toString()
            txtNutFoodProtein.text = food.protein.toString()
            txtNutFoodSugar.text = food.sugar.toString()
            txtNutFoodSodium.text = food.sodium.toString()
            txtNutFoodCalories.text = food.calories.toString()
            txtNutFoodCholesterol.text = food.cholesterol.toString()
            txtNutFoodPottasium.text = food.pottasium.toString()
            txtNutFoodTotalFat.text = food.total_fat.toString()
            txtNutFoodSaturatedFat.text = food.saturated_fat.toString()
            txtNutFoodCarbo.text = food.carbo_total.toString()
            txtNutFoodServing.text = food.serving.toString()

        }
    }
    override fun getItemCount() = foods.size
}