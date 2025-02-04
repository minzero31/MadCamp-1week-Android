package com.example.a3tabtest.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.a3tabtest.Contact
import com.example.a3tabtest.R

class AddContactDialog : DialogFragment() {

    private lateinit var nameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var addressEditText: EditText // 주소 필드 추가

    private var listener: AddContactListener? = null

    interface AddContactListener {
        fun onContactAdded(contact: Contact)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = targetFragment as? AddContactListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEditText = view.findViewById(R.id.nameEditText)
        phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText)
        addressEditText = view.findViewById(R.id.addressEditText) // 주소 필드 초기화

        view.findViewById<Button>(R.id.addButton).setOnClickListener {
            val name = nameEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()
            val address = addressEditText.text.toString() // 주소 필드 값 가져오기

            if (name.isNotEmpty() && phoneNumber.isNotEmpty() && address.isNotEmpty()) {
                val newContact = Contact(name, phoneNumber, address)
                Log.d("AddContactDialog", "Adding contact: $newContact")
                listener?.onContactAdded(newContact)
                dismiss()
            } else {
                Log.d("AddContactDialog", "Name, phone number, or address is empty")
                // Handle empty fields if necessary
            }
        }

        view.findViewById<Button>(R.id.cancelButton).setOnClickListener {
            dismiss()
        }
    }
}
