package manandhiman.attendancechecker.viewmodel

import android.app.Application
import android.widget.PopupMenu
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import manandhiman.attendancechecker.R
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

  val latestAttendanceBySubject =
    MutableLiveData(attendanceDao.getLastBySubject().toList())

  val historyAttendance = MutableLiveData(attendanceDao.getAll().toList().filter { it.totalDays != 0 })

  fun historyRecyclerViewAdapter(attendances: List<Attendance>): HistoryRecyclerView {

    val adapter = HistoryRecyclerView(attendances)

    adapter.setOnClickListener(object : HistoryRecyclerView.OnClickListener {
      override fun inflateMenu(holder: ConstraintLayout, attendance: Attendance) {
        val popupMenu = PopupMenu(getApplication(), holder)

        popupMenu.menuInflater.inflate(R.menu.item_history_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
          when(item.itemId) {
            R.id.menu_item_delete -> {
              Toast.makeText(getApplication(), "Now Deleting Entry", Toast.LENGTH_SHORT).show()
              deleteFromDB(attendance)
            }
          }
          true
        }
        popupMenu.show()
      }


    })

    return adapter
  }

  private fun deleteFromDB(attendance: Attendance) {
    attendanceDao.deleteAttendance(attendance)
    latestAttendanceBySubject.postValue(attendanceDao.getLastBySubject().toList())
    historyAttendance.postValue(attendanceDao.getAll().toList())
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
        latestAttendanceBySubject.postValue(attendanceDao.getLastBySubject())
      }

      override fun onMarkAbsent(id: String) {
        val prevAtt = attendanceDao.getLastById(id)

        val att = Attendance(
          prevAtt.subjectName, currentDate, "Absent", prevAtt.totalDays + 1, prevAtt.presentDays
        )
        attendanceDao.insert(att)
        latestAttendanceBySubject.postValue(attendanceDao.getLastBySubject())
      }

    })
    return adapter
  }

  fun searchHistory(searchQuery: String) {

    historyAttendance.postValue(attendanceDao.search(searchQuery).toList().filter { it.totalDays != 0 })

//    for(i in attendanceDao.search()) Log.d("tag db q", i.toString())
  }


}