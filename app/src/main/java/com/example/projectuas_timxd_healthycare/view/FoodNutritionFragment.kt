package com.example.projectuas_timxd_healthycare.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projectuas_timxd_healthycare.R
import com.example.projectuas_timxd_healthycare.model.Nutrition
import kotlinx.android.synthetic.main.fragment_food_nutrition.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import kotlin.jvm.Throws


class FoodNutritionFragment : Fragment() {
    var nutritions: ArrayList<Nutrition> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_nutrition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        val q = "3lb carrots and a chicken sandwich"
//        checkCalories(q)
        buttonCheck.setOnClickListener {
            val query = txtInputQuery.text.toString()
            checkCalories(query)
        }
    }

    fun updateList() {
        val layout = LinearLayoutManager(activity)
        view?.findViewById<RecyclerView>(R.id.RecViewFoodNut)?.let {
            it.layoutManager = layout
            it.setHasFixedSize(true)
            it.adapter = NutritionFoodListAdapter(foods = nutritions)
        }
    }




    fun checkCalories (q: String){
        nutritions.clear()

        val url2 =
            "https://api.calorieninjas.com/v1/nutrition?query=$q"
        val linkTrang = url2
        val queue = Volley.newRequestQueue(activity)
        val stringRequest = object: StringRequest(Request.Method.GET, linkTrang,
            Response.Listener<String> { response ->
                Log.d("A", "Response is: " + response)
                val res = JSONObject(response)
                val data = res.getJSONArray("items")

                for (i in 0 until data.length()) {
                    val playObj = data.getJSONObject(i)

                    with(playObj) {
                        nutritions.add(
                            Nutrition(
                                getString("name"),
                                getDouble("sugar_g"),
                                getDouble("fiber_g"),
                                getDouble("serving_size_g"),
                                getDouble("sodium_mg"),
                                getDouble("potassium_mg"),
                                getDouble("fat_saturated_g"),
                                getDouble("fat_total_g"),
                                getDouble("calories"),
                                getDouble("cholesterol_mg"),
                                getDouble("protein_g"),
                                getDouble("carbohydrates_total_g")
                            )
                        )
                    }
                }

                Log.d("cek nutrition", nutritions.toString())
                updateList()

            },
            Response.ErrorListener {  })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["X-Api-Key"] = "ckKwI7tLYyDKsVkZxc6eaA==UDrDCYD6yribidmX"
                return headers
            }
        }

        queue.add(stringRequest)
    }


}