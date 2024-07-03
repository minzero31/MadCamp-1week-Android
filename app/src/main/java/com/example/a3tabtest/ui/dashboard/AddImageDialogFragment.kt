package com.example.a3tabtest

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.example.a3tabtest.databinding.DialogAddImageBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddImageDialog : DialogFragment() {

    private var _binding: DialogAddImageBinding? = null
    private val binding get() = _binding!!
    private var selectedImageUri: Uri? = null
    private lateinit var currentPhotoPath: String

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
        lateinit var selectedstartDate: String
        lateinit var selectedendDate: String

        builder.setContentView(binding.root)

        binding.imageinsert.setOnClickListener {
            showImageSourceDialog()
        }
        binding.startpicker.setOnClickListener {
            showDatePickerDialog { year, month, dayOfMonth ->
                selectedstartDate = "$year/${month + 1}/$dayOfMonth"
                binding.startdate.text = selectedstartDate
            }
        }
        binding.endpicker.setOnClickListener {
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

    private fun showImageSourceDialog() {
        val options = arrayOf("카메라", "갤러리")
        android.app.AlertDialog.Builder(requireContext())
            .setTitle("이미지 선택")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }
            .show()
    }

    private fun openGallery() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PICK_IMAGE_REQUEST)
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CAMERA)
        } else {
            dispatchTakePictureIntent()
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "${requireContext().packageName}.fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_CAMERA)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            selectedImageUri?.let {
                binding.imageinsert.setImageURI(it)
            }
        } else if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            val imageFile = File(currentPhotoPath)
            selectedImageUri = Uri.fromFile(imageFile)
            selectedImageUri?.let {
                binding.imageinsert.setImageURI(it)
            }
        }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val REQUEST_CAMERA = 2
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
