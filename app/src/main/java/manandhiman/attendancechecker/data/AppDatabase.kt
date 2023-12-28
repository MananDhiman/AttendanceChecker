package manandhiman.attendancechecker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import manandhiman.attendancechecker.model.Attendance

@Database(entities = [Attendance::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
  abstract fun attendanceDao(): AttendanceDao
}