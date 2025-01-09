package com.kraos.querycalendar.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kraos.querycalendar.compose_widget.CalendarHistory
import com.kraos.querycalendar.compose_widget.CalendarHistory.DateConfigState
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

/**
 * 一个仿QQ聊天记录时间线的日历
 */
class TestCalenderHistoryActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置起始日期为 1997 年 1 月 1 日
        val startDate = Calendar.getInstance().apply {
            set(1997, Calendar.JANUARY, 1) // 注意月份从 0 开始
        }.time

        val monthlyDates = getMonthlyDates(startDate).map { (month, dates) ->
            CalendarHistory.MonthlyConfigState(month, dates)
        }

        setContent {
            CalendarHistory.DateListScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                monthlyDates,
                onClick = { date ->
                    // 点击日期的回调
                }
            )
        }

    }


    private fun getMonthlyDates(startDate: Date): Map<String, List<DateConfigState>> {
        val startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val endLocalDate = Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

        val groupedDates = mutableMapOf<String, MutableList<DateConfigState>>()

        var currentLocalDate = startLocalDate
        while (currentLocalDate <= endLocalDate) {
            val monthKey = currentLocalDate.format(DateTimeFormatter.ofPattern("yyyy年MM月"))
            val monthDates = mutableListOf<DateConfigState>()

            var dayOfMonth = currentLocalDate
            while (dayOfMonth.month == currentLocalDate.month && dayOfMonth <= endLocalDate) {
                monthDates.add(
                    DateConfigState(
                        Date.from(
                            dayOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant()
                        ),
                        CalendarHistory.DateControlBean.DateSignalBean((0..3).random() == 1)
                    )
                )
                dayOfMonth = dayOfMonth.plusDays(1)
            }

            groupedDates[monthKey] = monthDates
            currentLocalDate = currentLocalDate.plusMonths(1)
        }

        return groupedDates
    }


}