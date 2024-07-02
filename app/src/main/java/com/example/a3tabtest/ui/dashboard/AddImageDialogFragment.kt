package com.example.a3tabtest

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.a3tabtest.databinding.DialogAddImageBinding
import java.util.Calendar

class AddImageDialog : DialogFragment() {

    private var _binding: DialogAddImageBinding? = null
    private val binding get() = _binding!!
    private var selectedImageUri: Uri? = null

    interface AddImageListener {
        fun onImageAdded(uri: Uri, medicinename: String, takenday: String, isChecked: Boolean)
    }

    private var listener: AddImageListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = targetFragment as? AddImageListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddImageBinding.inflate(LayoutInflater.from(context))
        val builder = Dialog(requireContext())
        lateinit var selectedstartDate : String
        lateinit var selectedendDate : String

        builder.setContentView(binding.root)

        binding.imageinsert.setOnClickListener {
            openGallery()
        }
        binding.startpicker.setOnClickListener{
            showDatePickerDialog { year, month, dayOfMonth ->
                selectedstartDate = "$year/${month + 1}/$dayOfMonth"
                binding.startdate.text = selectedstartDate
            }
        }
        binding.endpicker.setOnClickListener{
            showDatePickerDialog { year, month, dayOfMonth ->
                selectedendDate = "$year/${month + 1}/$dayOfMonth"
                binding.enddate.text = selectedendDate
            }
        }

        binding.imageaddButton.setOnClickListener {
            val medicinename = binding.medicinename.text.toString()
            val takenday = "$selectedstartDate ~ $selectedendDate"
            selectedImageUri?.let {
                listener?.onImageAdded(it, medicinename, takenday, false)
            }
            dismiss()
        }
        binding.imagecancelButton.setOnClickListener {
            dismiss()
        }

        return builder
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            selectedImageUri?.let {
                binding.imageinsert.setImageURI(it)
            }
        }
    }
    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
    private fun showDatePickerDialog(onDateSet: (year: Int, month: Int, dayOfMonth: Int) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                onDateSet(selectedYear, selectedMonth, selectedDayOfMonth)
            },
            year, month, day
        )

        datePickerDialog.show()
    }


}