/*
    make text green when attendance more than 75%
    undo attendance one step
    clear all data
    add about
 */
package com.example.attendancechecker

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.attendancechecker.databinding.ActivityMainBinding
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var myEdit: SharedPreferences.Editor
    private var presentDays: Int = 0; private var totalDays: Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        preferences = getSharedPreferences("AttendanceChecker", Context.MODE_PRIVATE)
        myEdit = preferences.edit()

        initValues()
        setAttendanceInTextView()
        binding.mainButtonPresent.setOnClickListener(){
            markPresent()
        }
        binding.mainButtonAbsent.setOnClickListener(){
            markAbsent()
        }
    }

    fun initValues(){
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        binding.mainTextViewTodayDate.text = currentDate

        presentDays = preferences.getInt("presentDays",0); totalDays = preferences.getInt("totalDays",0)
    }

    private fun setAttendanceInTextView() {
        var percentage = ((presentDays.toDouble()/totalDays.toDouble())*100)
        val df = DecimalFormat("##.##")
        df.roundingMode = RoundingMode.FLOOR
        val roundOff = df.format(percentage)

        binding.mainTextViewAttendance.text = "$presentDays/$totalDays = $roundOff%"
    }

    private fun markPresent() {
        presentDays++; totalDays++
        myEdit.putInt("totalDays",totalDays); myEdit.putInt("presentDays",presentDays)
        myEdit.commit()
        Toast.makeText(applicationContext,"Present Marked", Toast.LENGTH_SHORT).show()
        setAttendanceInTextView()
    }

    private fun markAbsent(){
        totalDays++
        myEdit.putInt("totalDays",totalDays)
        myEdit.commit()
        Toast.makeText(applicationContext,"Absent Marked", Toast.LENGTH_SHORT).show()
        setAttendanceInTextView()
    }

}