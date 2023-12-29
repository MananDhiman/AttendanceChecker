package manandhiman.attendancechecker.utils

import java.math.RoundingMode
import java.text.DecimalFormat

object Utils {
  fun formattedPercentage(presentDays: Int, totalDays: Int): String {
    val percentage = ((presentDays.toDouble() / totalDays.toDouble()) * 100)
    val df = DecimalFormat("##.##")
    df.roundingMode = RoundingMode.FLOOR

    return df.format(percentage)
  }

}