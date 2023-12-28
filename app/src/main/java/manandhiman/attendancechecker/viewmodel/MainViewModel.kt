package manandhiman.attendancechecker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import manandhiman.attendancechecker.adapter.HistoryRecyclerView
import manandhiman.attendancechecker.data.AppDatabase
import manandhiman.attendancechecker.model.Attendance
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel(application: Application): AndroidViewModel(application) {

  private var presentDays: Int = 0; private var totalDays: Int =0
  private val sdf = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
  private val currentDate = sdf.format(Date())

  private val db = Room.databaseBuilder(
    getApplication(),
    AppDatabase::class.java, "attendance-record",
  ).allowMainThreadQueries().build()

  private val attendanceDao = db.attendanceDao()

  fun initVal(): String {
    if(attendanceDao.getLast()!=null){
      val lastAttendance = attendanceDao.getLast()

      presentDays = lastAttendance.presentDays
      totalDays = lastAttendance.totalDays

      return formattedAttendance()
    }
    return "No Previous Records Exist"
  }

  fun markPresent() {
    presentDays++; totalDays++
    val newAttendance = Attendance(totalDays,presentDays,currentDate,"Present")
    attendanceDao.insert(newAttendance)
  }

  fun markAbsent() {
    totalDays++
    val newAttendance = Attendance(totalDays,presentDays,currentDate,"Absent")
    attendanceDao.insert(newAttendance)
  }

  fun formattedAttendance() = "${presentDays}/${totalDays} = ${percentage()}"

  private fun percentage(): String {
    val percentage = ((presentDays.toDouble() / totalDays.toDouble()) * 100)
    val df = DecimalFormat("##.##")
    df.roundingMode = RoundingMode.FLOOR

    return df.format(percentage)
  }
  fun historyRecyclerViewAdapter(): HistoryRecyclerView {
    val listAttendance = attendanceDao.getAll()
    return HistoryRecyclerView(listAttendance)
  }
}