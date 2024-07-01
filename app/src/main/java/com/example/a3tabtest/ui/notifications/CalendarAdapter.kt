package com.example.a3tabtest.ui.notifications

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.a3tabtest.R
import java.text.SimpleDateFormat
import java.util.*

class CalendarAdapter(
    private val context: Context,
    private val dates: List<Date>,
    private val currentDate: Date
) : BaseAdapter() {

    private val dateFormat = SimpleDateFormat("d", Locale.KOREAN)
    private val monthFormat = SimpleDateFormat("M", Locale.KOREAN)
    private val currentMonth = monthFormat.format(currentDate)

    override fun getCount(): Int = dates.size

    override fun getItem(position: Int): Any = dates[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.calendar_day, parent, false)

        val date = dates[position]
        val dayText = view.findViewById<TextView>(R.id.dayText)
        dayText.text = dateFormat.format(date)

        val dateMonth = monthFormat.format(date)
        if (dateMonth != currentMonth) {
            dayText.setTextColor(context.resources.getColor(R.color.gray, null))
        } else {
            dayText.setTextColor(context.resources.getColor(R.color.black, null))
        }

        return view
    }
}
