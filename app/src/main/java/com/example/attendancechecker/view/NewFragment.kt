package com.example.attendancechecker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.attendancechecker.databinding.FragmentNewAttendanceBinding
import com.example.attendancechecker.model.Attendance
import com.example.attendancechecker.model.AttendanceDao
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class NewFragment(dao: AttendanceDao) : Fragment() {

    private val attendanceDao: AttendanceDao = dao
    private var presentDays: Int = 0; private var totalDays: Int =0
    private lateinit var binding: FragmentNewAttendanceBinding
    private val sdf = SimpleDateFormat("dd/M/yyyy")
    private val currentDate = sdf.format(Date())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewAttendanceBinding.inflate(layoutInflater, container,false)

        initValues()

        binding.mainButtonPresent.setOnClickListener { markPresent() }
        binding.mainButtonAbsent.setOnClickListener { markAbsent() }
        return binding.root
    }

    private fun initValues() {
        if(attendanceDao.getLast()!=null){
            val lastAttendance = attendanceDao.getLast()
            presentDays = lastAttendance.presentDays
            totalDays = lastAttendance.totalDays

            setAttendanceInTextView()
            return
        }

        binding.mainTextViewAttendance.text = "No Previous Records Exist"
    }

    private fun markPresent() {
        presentDays++; totalDays++
        val newAttendance = Attendance(totalDays,presentDays,currentDate,"Present")
        attendanceDao.insert(newAttendance)
        setAttendanceInTextView()
    }

    private fun markAbsent() {
        totalDays++
        val newAttendance = Attendance(totalDays,presentDays,currentDate,"Absent")
        attendanceDao.insert(newAttendance)
        setAttendanceInTextView()
    }

    private fun setAttendanceInTextView() {
        binding.mainTextViewTodayDate.text = currentDate

        val percentage = ((presentDays.toDouble()/totalDays.toDouble())*100)
        val df = DecimalFormat("##.##")
        df.roundingMode = RoundingMode.FLOOR
        val roundOff = df.format(percentage)

        binding.mainTextViewAttendance.text = "$presentDays/$totalDays = $roundOff%"
    }

}