package manandhiman.attendancechecker.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import manandhiman.attendancechecker.R
import manandhiman.attendancechecker.databinding.ActivityMainBinding
import manandhiman.attendancechecker.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

    val historyFragment = HistoryFragment()
    val newFragment = NewFragment()

    if (!viewModel.isSetup()) {
      loadFragment(SetupFragment())

      binding.btHistory.isEnabled = false
      binding.btNew.isEnabled = false
    }
    else loadFragment(newFragment)

    binding.btNew.setOnClickListener { loadFragment(newFragment) }
    binding.btHistory.setOnClickListener { loadFragment(historyFragment) }
  }

  private fun loadFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction().apply {
      replace(R.id.fl, fragment)
      commit()
    }
  }


}