package manandhiman.attendancechecker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import manandhiman.attendancechecker.R
import manandhiman.attendancechecker.model.Attendance
import manandhiman.attendancechecker.utils.Utils

class HistoryRecyclerView(private val attendanceList: List<Attendance>) :
  RecyclerView.Adapter<HistoryRecyclerView.ViewHolder>() {

  interface OnClickListener {
    fun inflateMenu(holder: ConstraintLayout, position: Attendance)
  }

  private var onClickListener: OnClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.attendance_record_item, parent, false)
    return ViewHolder(view)
  }

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val layout: ConstraintLayout = view.findViewById(R.id.layout)

    val textViewDate: TextView = view.findViewById(R.id.tv_date)
    val textViewAttendance: TextView = view.findViewById(R.id.tv_attendance)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val attendance = attendanceList[position]

    holder.layout.setOnClickListener {
      if(onClickListener != null) {
        onClickListener!!.inflateMenu(holder.layout, attendance)
      }
    }

    holder.textViewDate.text = "${attendance.date} ${attendance.subjectName} ${attendance.status}"

    holder.textViewAttendance.text =
      Utils.formattedCurrentAttendance(attendance.presentDays, attendance.totalDays)
  }

  override fun getItemCount(): Int = attendanceList.size

  fun setOnClickListener(onClickListener: OnClickListener) {
    this.onClickListener = onClickListener
  }
}