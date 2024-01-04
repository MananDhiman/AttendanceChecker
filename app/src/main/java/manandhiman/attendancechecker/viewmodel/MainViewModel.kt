package manandhiman.attendancechecker.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import manandhiman.attendancechecker.adapter.HistoryRecyclerView
import manandhiman.attendancechecker.adapter.NewRecyclerView
import manandhiman.attendancechecker.data.AppDatabase
import manandhiman.attendancechecker.model.Attendance
import manandhiman.attendancechecker.model.Subject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel(application: Application) : AndroidViewModel(application) {

  private val sdf = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
  private val currentDate = sdf.format(Date())


  private val db = Room.databaseBuilder(
    getApplication(),
    AppDatabase::class.java, "attendance-record",
  ).allowMainThreadQueries().build()

  private val attendanceDao = db.attendanceDao()
  private val subjectDao = db.subjectDao()

  private val l = attendanceDao.getLastBySubject().toList()

  val latestAttendanceBySubject = MutableLiveData(l)

  fun historyRecyclerViewAdapter(): HistoryRecyclerView {
    val listAttendance = attendanceDao.getAll().filter { it.totalDays != 0 }
    return HistoryRecyclerView(listAttendance)
  }

  fun isSetup() = subjectDao.getAllSubjects().isNotEmpty()

  fun addSubjectsToDB(subjectNames: ArrayList<Subject>) {
    subjectDao.addSubjects(subjectNames)
    for (i in subjectNames) {
      val att = Attendance(i.name, "", "", 0, 0)
      attendanceDao.insert(att)
    }

  }

  fun getNewAttendanceRVAdapter(): NewRecyclerView {
    Log.d("log",latestAttendanceBySubject.value.toString())
    val adapter = NewRecyclerView(latestAttendanceBySubject.value!!)

    adapter.setOnClickListener(object : NewRecyclerView.OnClickListener {
      override fun onMarkPresent(id: String) {
        val prevAtt = attendanceDao.getLastById(id)

        val att = Attendance(
          prevAtt.subjectName,
          currentDate,
          "Present",
          prevAtt.totalDays + 1,
          prevAtt.presentDays + 1
        )
        attendanceDao.insert(att)

        latestAttendanceBySubject.value = attendanceDao.getLastBySubject()
      }

      override fun onMarkAbsent(id: String) {
        val prevAtt = attendanceDao.getLastById(id)

        val att = Attendance(
          prevAtt.subjectName, currentDate, "Absent", prevAtt.totalDays + 1, prevAtt.presentDays
        )
        attendanceDao.insert(att)
        latestAttendanceBySubject.value = attendanceDao.getLastBySubject()
      }

    })
    return adapter
  }


}