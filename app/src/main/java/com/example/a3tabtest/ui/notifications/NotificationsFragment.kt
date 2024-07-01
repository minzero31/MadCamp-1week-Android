package com.example.a3tabtest.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.a3tabtest.R
import java.text.SimpleDateFormat
import java.util.*

class NotificationsFragment : Fragment() {

    private lateinit var calendarGridView: GridView
    private lateinit var currentMonthYear: TextView
    private lateinit var prevMonthButton: ImageButton
    private lateinit var nextMonthButton: ImageButton
    private var calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy년 M월", Locale.KOREAN)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        calendarGridView = root.findViewById(R.id.calendarGridView)
        currentMonthYear = root.findViewById(R.id.currentMonthYear)
        prevMonthButton = root.findViewById(R.id.prevMonthButton)
        nextMonthButton = root.findViewById(R.id.nextMonthButton)

        updateCalendar()

        prevMonthButton.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateCalendar()
        }

        nextMonthButton.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateCalendar()
        }

        return root
    }

    private fun updateCalendar() {
        currentMonthYear.text = dateFormat.format(calendar.time)
        val days = ArrayList<Date>()
        val monthCalendar = calendar.clone() as Calendar
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth)

        while (days.size < 42) {
            days.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val adapter = CalendarAdapter(requireContext(), days, calendar.time)
        calendarGridView.adapter = adapter
    }
}
