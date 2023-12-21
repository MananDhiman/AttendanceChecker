package manandhiman.attendancechecker.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AttendanceDao {
  @Query("SELECT * FROM attendance ORDER BY ID DESC")
  fun getAll(): List<Attendance>

  @Query("SELECT * FROM attendance ORDER BY ID DESC LIMIT 1")
  fun getLast(): Attendance

  @Insert
  fun insert(vararg attendance: Attendance)

  @Query("DELETE FROM attendance WHERE id = :id")
  fun deleteAttendanceById(id: Int)
}