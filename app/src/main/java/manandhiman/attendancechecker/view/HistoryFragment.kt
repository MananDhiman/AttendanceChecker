package manandhiman.attendancechecker.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import manandhiman.attendancechecker.databinding.FragmentHistoryBinding
import manandhiman.attendancechecker.viewmodel.MainViewModel

class HistoryFragment : Fragment() {

  private lateinit var binding: FragmentHistoryBinding
  private lateinit var viewModel: MainViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)

    viewModel = ViewModelProvider(this)[MainViewModel::class.java]

    binding.rv.layoutManager = LinearLayoutManager(context)

    binding.btSearch.setOnClickListener {
      val searchText = binding.etSearchQuery.text
      if(!searchText.isNullOrBlank()) {
        Log.d("tag", "now searching")
        viewModel.searchHistory(searchText.toString())
      }
    }

    viewModel.historyAttendance.observe(viewLifecycleOwner) {
      binding.rv.adapter = viewModel.historyRecyclerViewAdapter(it)
    }
    return binding.root
  }

}