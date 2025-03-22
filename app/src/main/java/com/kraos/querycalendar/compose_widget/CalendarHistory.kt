package com.kraos.querycalendar.compose_widget

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.kraos.querycalendar.compose_widget.CalendarHistory.DateConfigState
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.time.Duration.Companion.seconds

/**
 * @author kraos
 * @date 2025/1/9
 * @desc 日历历史记录的控件
 */
object CalendarHistory {

    /**
     * 实现具体业务的参数接口
     */
    sealed interface DateControlBean {
        /**
         * 只需要选中状态的日期
         */
        data class DateSignalBean(
            val signal: Boolean,
        ) : DateControlBean
    }

    /**
     * 日期的包装业务类
     */
    data class DateConfigState(
        val date: Date,
        //是否标记
        val bean: DateControlBean,
    ) {
        companion object {
            private val simpleDateFormat = SimpleDateFormat("d", Locale.US)
        }

        @delegate:Transient
        val dateString: String by lazy { simpleDateFormat.format(date) }
    }

    /**
     * 月份的包装类
     */
    data class MonthlyConfigState(
        val month: String,
        val dates: List<DateConfigState>,
    ) {

        companion object {
            private val calendar = Calendar.getInstance()
        }

        @delegate:Transient
        val formatToWeekDayIndex by lazy {
            calendar.time = dates.first().date
            calendar.get(Calendar.DAY_OF_WEEK)
        }
    }


    @Composable
    private fun CalendarCell(
        state: DateConfigState,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val text = state.dateString
        Box(
            modifier = modifier
                .aspectRatio(1f)
                .fillMaxSize()
                .padding(2.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onClick()
                }
        ) {
            when (state.bean) {
                is DateControlBean.DateSignalBean -> {
                    val signal = (state.bean).signal
                    Text(
                        text = text,
                        color = if (signal) Color(0xFF1A1A1A) else Color(0xFFAFAFAF),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }

    private fun Int.getDayOfWeek3Letters(): String? = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_WEEK, this@getDayOfWeek3Letters)
    }.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())

    @Composable
    private fun WeekdayCell(weekday: Int, modifier: Modifier = Modifier) {
        val text = weekday.getDayOfWeek3Letters()
        Box(
            modifier = modifier
                .aspectRatio(1f)
                .fillMaxSize()
        ) {
            Text(
                text = text.orEmpty(),
                color = colorScheme.onPrimaryContainer,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }


    @Composable
    private fun CalendarGrid(
        modifier: Modifier = Modifier,
        weekdayFirstIndex: Int,
        date: List<DateConfigState>,
        onClick: (Date) -> Unit,
        startFromSunday: Boolean,
        //是否需要显示星期表头
        showWeekdayHeader: Boolean = false,
    ) {
        CalendarCustomLayout(modifier = modifier) {
            if (showWeekdayHeader) {
                val weekdays = getWeekDays(startFromSunday)
                weekdays.forEach {
                    WeekdayCell(weekday = it)
                }
            }
            // Adds Spacers to align the first day of the month to the correct weekday
            repeat(if (!startFromSunday) weekdayFirstIndex - 2 else weekdayFirstIndex - 1) {
                Spacer(modifier = Modifier)
            }
            date.fastForEach { state ->
                CalendarCell(state = state, onClick = { onClick(state.date) })
            }
        }
    }

    private fun getWeekDays(startFromSunday: Boolean): List<Int> {
        val listDay = (1..7).toList()
        return (if (startFromSunday) listDay else listDay.drop(1) + listDay.take(1))
    }

    @Composable
    private fun CalendarCustomLayout(
        modifier: Modifier = Modifier,
        horizontalGapDp: Dp = 2.dp,
        verticalGapDp: Dp = 2.dp,
        content: @Composable () -> Unit,
    ) {
        val horizontalGap = with(LocalDensity.current) {
            horizontalGapDp.roundToPx()
        }
        val verticalGap = with(LocalDensity.current) {
            verticalGapDp.roundToPx()
        }
        Layout(
            content = content,
            modifier = modifier,
        ) { measurables, constraints ->
            val totalWidthWithoutGap = constraints.maxWidth - (horizontalGap * 6)
            val singleWidth = totalWidthWithoutGap / 7

            val xPos: MutableList<Int> = mutableListOf()
            val yPos: MutableList<Int> = mutableListOf()
            var currentX = 0
            var currentY = 0
            measurables.forEach { _ ->
                xPos.add(currentX)
                yPos.add(currentY)
                if (currentX + singleWidth + horizontalGap > totalWidthWithoutGap) {
                    currentX = 0
                    currentY += singleWidth + verticalGap
                } else {
                    currentX += singleWidth + horizontalGap
                }
            }

            val placeables: List<Placeable> = measurables.map { measurable ->
                measurable.measure(
                    constraints.copy(
                        maxHeight = singleWidth,
                        maxWidth = singleWidth
                    )
                )
            }

            layout(
                width = constraints.maxWidth,
                height = currentY + singleWidth + verticalGap,
            ) {
                placeables.forEachIndexed { index, placeable ->
                    placeable.placeRelative(
                        x = xPos[index],
                        y = yPos[index],
                    )
                }
            }
        }
    }


    @Composable
    private fun MonthCalendar(
        modifier: Modifier = Modifier,
        weekdayFirstIndex: Int,
        month: String, // 月份标识，例如 "2023-01"
        dates: List<DateConfigState>, // 对应日期
        onClick: (Date) -> Unit,
        startFromSunday: Boolean = true,
    ) {
        Column(modifier = modifier) {
            Text(
                text = month,
                color = Color(0xFFAFAFAF),
                modifier = Modifier.padding(horizontal = 26.dp, vertical = 8.dp)
            )
            //分割线
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFAFAFAF).copy(0.5f))
            )
            CalendarGrid(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp),
                weekdayFirstIndex = weekdayFirstIndex,
                date = dates,
                onClick = onClick,
                startFromSunday = startFromSunday,
            )
        }
    }


    @Composable
    fun DateListScreen(
        modifier: Modifier = Modifier,
        monthlyState: List<MonthlyConfigState>,
        onClick: (Date) -> Unit,
    ) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            monthlyState.fastForEach { monthlyConfigState ->
                item(
                    key = monthlyConfigState.month,
                ) {
                    MonthCalendar(
                        weekdayFirstIndex = monthlyConfigState.formatToWeekDayIndex,
                        month = monthlyConfigState.month,
                        dates = monthlyConfigState.dates,
                        onClick = onClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }


}

@Preview
@Composable
fun PreViewCalendarHistory() {
    // 设置起始日期为 1997 年 1 月 1 日
    val startDate = Calendar.getInstance().apply {
        set(1997, Calendar.JANUARY, 1) // 注意月份从 0 开始
    }.time

    val monthlyDates = remember {
        getMonthlyDates(startDate).map { (month, dates) ->
            CalendarHistory.MonthlyConfigState(month, dates)
        }
    }

    CalendarHistory.DateListScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        monthlyDates,
        onClick = {}
    )
}

private fun getMonthlyDates(startDate: Date): Map<String, List<DateConfigState>> {
    val calendar = Calendar.getInstance()
    val endDate = calendar.time
    val groupedDates = mutableMapOf<String, MutableList<DateConfigState>>()

    calendar.time = startDate

    while (!calendar.time.after(endDate)) {
        val monthKey = SimpleDateFormat("yyyy年MM月", Locale.getDefault()).format(calendar.time)
        groupedDates.getOrPut(monthKey) { mutableListOf() }
            .add(
                DateConfigState(
                    calendar.time,
                    CalendarHistory.DateControlBean.DateSignalBean((0..3).random() == 1)
                )
            )
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }
    return groupedDates
}

