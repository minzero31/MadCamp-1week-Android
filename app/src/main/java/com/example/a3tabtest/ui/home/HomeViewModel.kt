package com.example.a3tabtest.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "" // 초기값은 비워둡니다.
    }
    val text: LiveData<String> = _text

    // MainActivity에서 JSON 데이터를 받아오는 메서드
    fun updateMessage(newMessage: String) {
        _text.value = newMessage
    }
}