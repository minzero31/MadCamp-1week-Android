package com.example.a3tabtest

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.a3tabtest.databinding.DialogAddImageBinding

class AddImageDialog : DialogFragment() {

    private var _binding: DialogAddImageBinding? = null
    private val binding get() = _binding!!
    private var selectedImageUri: Uri? = null

    interface AddImageListener {
        fun onImageAdded(uri: Uri)
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
        builder.setContentView(binding.root)
        binding.imageinsert.setOnClickListener {
            openGallery()
        }
        binding.imageaddButton.setOnClickListener {
            selectedImageUri?.let {
                listener?.onImageAdded(it)
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


}