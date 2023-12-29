package manandhiman.attendancechecker.view

import android.os.Bundle
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
    binding.rv.adapter = viewModel.historyRecyclerViewAdapter()

    return binding.root
  }

}