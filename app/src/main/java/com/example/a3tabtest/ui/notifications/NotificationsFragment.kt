package com.example.a3tabtest.ui.notifications

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.a3tabtest.R
import com.example.a3tabtest.ui.dashboard.RecyclerModel
import java.text.SimpleDateFormat
import java.util.*

class NotificationsFragment : Fragment() {

    private lateinit var calendarGridView: GridView
    private lateinit var currentMonthYear: TextView
    private lateinit var prevMonthButton: ImageButton
    private lateinit var nextMonthButton: ImageButton
    private lateinit var weekdayHeader: LinearLayout
    private var itemList: MutableList<RecyclerModel> = mutableListOf()
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
        weekdayHeader = root.findViewById(R.id.weekdayHeader)
        loadSavedData()
        // 요일 초기화 (필요할 경우)
        val weekdays = arrayOf("일", "월", "화", "수", "목", "금", "토")
        for (i in 0 until weekdayHeader.childCount) {
            (weekdayHeader.getChildAt(i) as TextView).text = weekdays[i]
        }

        updateCalendar()

        prevMonthButton.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateCalendar()
        }

        nextMonthButton.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateCalendar()
        }
        Glide.with(this)
            .asGif()
            .load(R.drawable.seaweed)
            .into(root.findViewById(R.id.weed))
        return root
    }

    private fun updateCalendar() {
        currentMonthYear.text = dateFormat.format(calendar.time)
        val days = ArrayList<Date?>()
        val monthCalendar = calendar.clone() as Calendar
        val cal = Calendar.getInstance().time
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth)

        while (days.size < 42) {
            if (monthCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
                days.add(monthCalendar.time)
            } else {
                days.add(null)
            }
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val adapter = CalendarAdapter(requireContext(), days, calendar.time, itemList, cal)
        calendarGridView.adapter = adapter
    }
    fun loadSavedData() {
        val sharedPref = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        itemList.clear()

        for (i in 0 until sharedPref.all.size / 4) { // 각 항목당 3개의 데이터 (uri, medicinename, takenday)
            val uri = sharedPref.getString("uri_$i", null)?.let { Uri.parse(it) }
            val medicinename = sharedPref.getString("medicinename_$i", "")
            val takenday = sharedPref.getString("takenday_$i", "")
            val isChecked = sharedPref.getBoolean("isChecked_$i", false)

            if (uri != null && medicinename != null && takenday != null) {
                itemList.add(RecyclerModel(null, uri, medicinename, takenday, isChecked))
            }
        }
    }
}

