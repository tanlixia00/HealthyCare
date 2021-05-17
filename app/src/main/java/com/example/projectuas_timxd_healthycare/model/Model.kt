package com.example.projectuas_timxd_healthycare.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Gender(val id:Int, val nama:String){
    override fun toString(): String {
        return nama
    }
}

@Entity
data class User(
    @ColumnInfo(name="name")
    var name:String,
    @ColumnInfo(name="age")
    var age:String,
    @ColumnInfo(name="gender")
    var gender:String,
    @ColumnInfo(name="weight")
    var weight:String,
    @ColumnInfo(name="height")
    var height:String,
    @ColumnInfo(name="goal")
    var goal:String,
    @ColumnInfo(name="bmr")
    val bmr:Int
){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0
}
