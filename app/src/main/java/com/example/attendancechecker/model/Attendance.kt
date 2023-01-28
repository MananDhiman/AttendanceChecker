package com.example.attendancechecker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Attendance(
    val totalDays: Int,
    val presentDays: Int,
    val date: String,
    val status: String
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null

}
