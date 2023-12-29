package manandhiman.attendancechecker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import manandhiman.attendancechecker.model.Attendance
import manandhiman.attendancechecker.model.Subject

@Database(entities = [Attendance::class, Subject::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun attendanceDao(): AttendanceDao
  abstract fun subjectDao(): SubjectDao
}