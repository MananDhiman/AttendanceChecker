package manandhiman.attendancechecker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import manandhiman.attendancechecker.model.Subject

@Dao
interface SubjectDao {

  @Insert
  fun addSubject(subject: Subject)

  @Query("SELECT * FROM subject;")
  fun getAllSubjects(): List<Subject>

  @Query("SELECT name FROM subject WHERE id=:id;")
  fun getSubject(id: Int): String
  @Insert
  fun addSubjects(subjectNames: ArrayList<Subject>)
}