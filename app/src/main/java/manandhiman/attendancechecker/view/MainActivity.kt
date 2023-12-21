package manandhiman.attendancechecker.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.room.Room
import manandhiman.attendancechecker.R
import manandhiman.attendancechecker.databinding.ActivityMainBinding
import manandhiman.attendancechecker.model.AppDatabase

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val db = Room.databaseBuilder(
      this,
      AppDatabase::class.java, "attendance-record",
    ).allowMainThreadQueries().build()
    val attendanceDao = db.attendanceDao()

    val historyFragment = HistoryFragment(attendanceDao)
    val newFragment = NewFragment(attendanceDao)

    loadFragment(newFragment)

    binding.buttonNew.setOnClickListener { loadFragment(newFragment) }
    binding.buttonHistory.setOnClickListener { loadFragment(historyFragment) }
  }

  private fun loadFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction().apply {
      replace(R.id.frameLayout,fragment)
      commit()
    }
  }


}