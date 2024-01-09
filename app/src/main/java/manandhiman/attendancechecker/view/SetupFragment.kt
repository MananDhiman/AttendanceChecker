package manandhiman.attendancechecker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import manandhiman.attendancechecker.databinding.FragmentSetupBinding
import manandhiman.attendancechecker.model.Subject
import manandhiman.attendancechecker.viewmodel.MainViewModel

class SetupFragment : Fragment() {
  private lateinit var binding: FragmentSetupBinding
  private lateinit var viewModel: MainViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    binding = FragmentSetupBinding.inflate(layoutInflater, container, false)

    viewModel = ViewModelProvider(this)[MainViewModel::class.java]

    binding.bt.setOnClickListener {
      val num = binding.etNumber.text.toString().toInt()
      if (isNumberValid(num)) {
        createForm(num)
      }
    }

    return binding.root
  }

  private fun createForm(num: Int) {
    // todo refactor
    binding.ll.removeAllViews()
    val editTextList = ArrayList<EditText>()

    for (i in 0..<num) {
      val editText = EditText(context)
      editText.hint = "Subject Name"
      editTextList.add(editText)
      binding.ll.addView(editText)
    }

    val btn = Button(context)
    btn.text = "Save Subjects"

    binding.ll.addView(btn)

    btn.setOnClickListener {
      if (isListValid(editTextList)) {
        // todo add subjects list to db, viewmodel
        val subjects = ArrayList<Subject>()
        for (i in editTextList) subjects.add(Subject(i.text.trim().toString()))
        viewModel.addSubjectsToDB(subjects)

        Toast.makeText(context, "Will relaunch app in 2 seconds. Please wait", Toast.LENGTH_LONG)
          .show()
        activity?.recreate()

      }

    }

  }

  private fun isListValid(editTextList: ArrayList<EditText>): Boolean {
    // todo string validate to prevent SQL 'injection'
    for (i in editTextList) {

      if (i.text.isEmpty()) {
        Toast.makeText(context, "A subject name is empty", Toast.LENGTH_SHORT).show()
        i.requestFocus()
        return false
      } else if (i.text.toString().length >= 75) {
        Toast.makeText(
          context, "A subject name is quite long. Make sure it's correct", Toast.LENGTH_SHORT
        ).show()
        i.requestFocus()
        return false
      }
    }

    return true
  }

  private fun isNumberValid(num: Int): Boolean {

    if (num < 1) {
      Toast.makeText(context, "Enter a number greater than 0", Toast.LENGTH_SHORT).show()
      return false
    } else if (num >= 10) {
      Toast.makeText(
        context, "You're entering a large number. Make sure it's correct", Toast.LENGTH_SHORT
      ).show()
      return true
    }
    return true
  }

}