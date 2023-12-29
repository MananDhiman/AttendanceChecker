package manandhiman.attendancechecker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import manandhiman.attendancechecker.R
import manandhiman.attendancechecker.model.Attendance
import manandhiman.attendancechecker.utils.Utils

class NewRecyclerView(private val list: List<Attendance>) :
  RecyclerView.Adapter<NewRecyclerView.ViewHolder>() {

  interface OnClickListener {
    fun onMarkPresent(id: String)
    fun onMarkAbsent(id: String)
  }

  private var onClickListener: OnClickListener? = null

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textViewName: TextView = view.findViewById(R.id.tv_subject_name)
    val textViewAttendance: TextView = view.findViewById(R.id.tv_subject_attendance)
    val btPresent: Button = view.findViewById(R.id.bt_present)
    val btAbsent: Button = view.findViewById(R.id.bt_absent)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.new_attendance_item, parent, false)
    return ViewHolder(view)
  }

  override fun getItemCount() = list.size


  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val attendance: Attendance = list[position]

    holder.textViewName.text = attendance.subjectName

    val formattedPercentage = Utils.formattedPercentage(attendance.presentDays, attendance.totalDays)
    holder.textViewAttendance.text =
      "${attendance.presentDays}/${attendance.totalDays} = $formattedPercentage%"

    holder.btPresent.setOnClickListener {

      if (onClickListener != null) {
        onClickListener!!.onMarkPresent(attendance.subjectName)
      }
    }

    holder.btAbsent.setOnClickListener {
      if (onClickListener != null) {
        onClickListener!!.onMarkAbsent(attendance.subjectName)
      }
    }

  }

  fun setOnClickListener(onClickListener: OnClickListener) {
    this.onClickListener = onClickListener
  }
}
