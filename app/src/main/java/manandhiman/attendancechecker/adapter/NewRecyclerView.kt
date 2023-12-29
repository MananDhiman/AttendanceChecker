package manandhiman.attendancechecker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import manandhiman.attendancechecker.R
import manandhiman.attendancechecker.model.Attendance
import java.math.RoundingMode
import java.text.DecimalFormat

class NewRecyclerView(private val list: List<Attendance>):
RecyclerView.Adapter<NewRecyclerView.ViewHolder>() {

  interface OnClickListener {
    fun onMarkPresent(id: String)
    fun onMarkAbsent(id: String)
  }

  private var onClickListener: OnClickListener? = null

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textViewName: TextView = view.findViewById(R.id.ntvSubjectName)
    val textViewAttendance: TextView = view.findViewById(R.id.ntvSubjectAttendance)
    val btPresent: Button = view.findViewById(R.id.btPresent)
    val btAbsent: Button = view.findViewById(R.id.btAbsent)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.new_attendance_item, parent, false)
    return ViewHolder(view)
  }

  override fun getItemCount() = list.size


  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val attendance: Attendance = list[position]

    val percentage = ((attendance.presentDays.toDouble() / attendance.totalDays.toDouble()) * 100)
    val df = DecimalFormat("##.##")
    df.roundingMode = RoundingMode.FLOOR
    val percentageRoundOff = df.format(percentage)


    holder.textViewName.text = attendance.subjectName
    holder.textViewAttendance.text =
      "${attendance.presentDays}/${attendance.totalDays} = $percentageRoundOff%"

    holder.btPresent.setOnClickListener {

      if (onClickListener != null) {
        onClickListener!!.onMarkPresent(attendance.subjectName)
      }
    }

    holder.btAbsent.setOnClickListener {
      if (onClickListener != null) {
        onClickListener!!.onMarkAbsent(attendance.subjectName)
//      }
      }
    }

  }

  fun setOnClickListener(onClickListener: OnClickListener) {
    this.onClickListener = onClickListener
  }
}
