package com.example.a3tabtest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.a3tabtest.R

class ViewContactDialog : DialogFragment() {

    companion object {
        private const val ARG_NAME = "name"
        private const val ARG_PHONE_NUMBER = "phoneNumber"
        private const val ARG_ADDRESS = "address"

        fun newInstance(name: String, phoneNumber: String, address: String): ViewContactDialog {
            val args = Bundle()
            args.putString(ARG_NAME, name)
            args.putString(ARG_PHONE_NUMBER, phoneNumber)
            args.putString(ARG_ADDRESS, address)
            val fragment = ViewContactDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_view_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = arguments?.getString(ARG_NAME)
        val phoneNumber = arguments?.getString(ARG_PHONE_NUMBER)
        val address = arguments?.getString(ARG_ADDRESS) // 주소 가져오기

        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val phoneNumberTextView: TextView = view.findViewById(R.id.phoneNumberTextView)
        val addressTextView: TextView = view.findViewById(R.id.addressTextView) // 주소 TextView 초기화

        nameTextView.text = name
        phoneNumberTextView.text = phoneNumber
        addressTextView.text = address // 주소 설정
        addressTextView.visibility =
            if (address.isNullOrEmpty()) View.GONE else View.VISIBLE // 주소가 있는 경우에만 보이도록 처리
    }
}
