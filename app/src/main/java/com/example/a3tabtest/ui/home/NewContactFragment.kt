// NewContactFragment.kt

package com.example.a3tabtest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a3tabtest.R

class NewContactFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_add_contact, container, false)

        // 여기서 추가 및 취소 버튼의 클릭 이벤트 처리를 할 수 있습니다.

        return root
    }

}
