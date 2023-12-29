package manandhiman.attendancechecker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import manandhiman.attendancechecker.databinding.FragmentNewBinding
import manandhiman.attendancechecker.viewmodel.MainViewModel

class NewFragment : Fragment() {

  private lateinit var viewModel: MainViewModel
  private lateinit var binding: FragmentNewBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentNewBinding.inflate(layoutInflater, container,false)

    viewModel = ViewModelProvider(this)[MainViewModel::class.java]

    binding.recyclerView.layoutManager = LinearLayoutManager(context)
    binding.recyclerView.adapter = viewModel.getNewAttendanceRVAdapter()

//    binding.mainTextViewAttendance.text = viewModel.initVal()
//
//    binding.mainButtonPresent.setOnClickListener {
//      viewModel.markPresent()
//      setAttendanceInTextView()
//    }
//
//    binding.mainButtonAbsent.setOnClickListener {
//      viewModel.markAbsent()
//      setAttendanceInTextView()
//    }
    return binding.root
  }

  private fun setAttendanceInTextView() {
    binding.mainTextViewAttendance.text = viewModel.formattedAttendance()
  }

}