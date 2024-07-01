package com.example.a3tabtest.ui.notifications

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.a3tabtest.R
import java.text.SimpleDateFormat
import java.util.*

class CalendarAdapter(
    private val context: Context,
    private val dates: List<Date?>, // Date 배열에 null을 허용하여 비어 있는 셀을 나타냄
    private val currentDate: Date
) : BaseAdapter() {

    private val dateFormat = SimpleDateFormat("d", Locale.KOREAN)
    private val monthFormat = SimpleDateFormat("M", Locale.KOREAN)
    private val currentMonth = monthFormat.format(currentDate)

    override fun getCount(): Int = dates.size

    override fun getItem(position: Int): Any? = dates[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.calendar_day, parent, false)

        val date = dates[position]
        val dayText = view.findViewById<TextView>(R.id.dayText)

        if (date != null) {
            dayText.text = dateFormat.format(date)

            val dateMonth = monthFormat.format(date)
            if (dateMonth != currentMonth) {
                dayText.setTextColor(ContextCompat.getColor(context, R.color.greyed_out))
            } else {
                dayText.setTextColor(ContextCompat.getColor(context, android.R.color.black))
            }

            view.visibility = View.VISIBLE // 현재 달의 날짜인 경우 보이도록 설정

            // 클릭 리스너 추가
            dayText.setOnClickListener {
                showDateDialog(date)
            }
        } else {
            dayText.text = ""
            view.visibility = View.INVISIBLE // 비어 있는 셀은 숨김
        }

        return view
    }

    private fun showDateDialog(date: Date) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_date_info)

        val calendar = Calendar.getInstance()
        calendar.time = date

        val dateFormat = SimpleDateFormat("yyyy년 M월 d일 (E)", Locale.KOREAN)
        val dateString = dateFormat.format(date)
        dialog.findViewById<TextView>(R.id.dateInfoText).text = dateString

        dialog.findViewById<TextView>(R.id.medicationText).text = "<복용한 약>"
        dialog.findViewById<TextView>(R.id.bottomText).text = "우하하"

        dialog.show()
    }
}
