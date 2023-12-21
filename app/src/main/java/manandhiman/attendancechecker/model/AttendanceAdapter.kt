package manandhiman.attendancechecker.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import manandhiman.attendancechecker.R
import java.math.RoundingMode
import java.text.DecimalFormat

class AttendanceAdapter(private val attendanceList: List<Attendance>):
  RecyclerView.Adapter<AttendanceAdapter.ViewHolder>(){

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.attendance_record_item, parent, false)
    return ViewHolder(view)
  }

  class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
    val textViewDate: TextView = view.findViewById(R.id.textViewDate)
    val textViewAttendance: TextView = view.findViewById(R.id.textViewAttendance)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val attendance = attendanceList[position]

    holder.textViewDate.text = "${attendance.date} ${attendance.status}"

    val percentage = ((attendance.presentDays.toDouble()/attendance.totalDays.toDouble())*100)
    val df = DecimalFormat("##.##")
    df.roundingMode = RoundingMode.FLOOR
    val percentageRoundOff = df.format(percentage)
    holder.textViewAttendance.text =
      "${attendance.presentDays}/${attendance.totalDays} = $percentageRoundOff%"
  }

  override fun getItemCount(): Int = attendanceList.size
}