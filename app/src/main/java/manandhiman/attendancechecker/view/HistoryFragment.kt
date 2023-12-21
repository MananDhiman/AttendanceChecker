package manandhiman.attendancechecker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import manandhiman.attendancechecker.databinding.FragmentHistoryBinding
import manandhiman.attendancechecker.model.AttendanceAdapter
import manandhiman.attendancechecker.model.AttendanceDao

class HistoryFragment(dao: AttendanceDao) : Fragment() {

  private val attendanceDao: AttendanceDao = dao
  private lateinit var binding: FragmentHistoryBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentHistoryBinding.inflate(layoutInflater, container,false)

    binding.recyclerView.layoutManager = LinearLayoutManager(context)
    loadAttendanceHistory()

    return binding.root
  }

  private fun loadAttendanceHistory() {
    val listAttendance = attendanceDao.getAll()
    val adapter = AttendanceAdapter(listAttendance)
    binding.recyclerView.adapter = adapter
  }

}