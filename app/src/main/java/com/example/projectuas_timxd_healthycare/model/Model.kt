package com.example.projectuas_timxd_healthycare.model

import androidx.room.*

data class Gender(val id: Int, val nama: String) {
    override fun toString(): String {
        return nama
    }
}

@Entity
data class User(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "age")
    var age: String,
    @ColumnInfo(name = "gender")
    var gender: String,
    @ColumnInfo(name = "weight")
    var weight: String,
    @ColumnInfo(name = "height")
    var height: String,
    @ColumnInfo(name = "goal")
    var goal: String,
    @ColumnInfo(name = "bmr")
    var bmr: Int,
    @ColumnInfo(name = "maxCal")
    var maxCal: Int,
    @ColumnInfo(name = "sumCal")
    var sumCal: Int,
    @ColumnInfo(name = "status")
    var status: String
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}

@Entity
data class Food(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "cal")
    var cal: String,
    @ColumnInfo(name = "userId")
    var userId: Int,
    @ColumnInfo(name = "date")
    var date: Long
) {
    @PrimaryKey(autoGenerate = true)
    var idFood: Int = 0
}

@Entity
data class Report(
    @ColumnInfo(name = "date")
    var date: Long,
    @ColumnInfo(name = "lastStatus")
    var lastStatus: String,
    @ColumnInfo(name = "totalMeals")
    var totalMeals: Int,
    @ColumnInfo(name = "totalCal")
    var totalCal: Int
) {
    @PrimaryKey(autoGenerate = true)
    var idReport: Int = 0
}

data class UserWithFoods(
    @Embedded val user: User,
    @Relation(
        parentColumn = "uuid",
        entityColumn = "userId"
    )
    val foods: List<Food>
)
