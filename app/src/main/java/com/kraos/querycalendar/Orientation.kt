import com.kraos.querycalendar.HorizontalAlignment
import com.kraos.querycalendar.HorizontalAnimationStyle
import com.kraos.querycalendar.VerticalAlignment
import com.kraos.querycalendar.VerticalAnimationStyle

sealed class Orientation {
    data class Vertical(val alignment: VerticalAlignment = VerticalAlignment.BottomToTop,
                        val animationStyle: VerticalAnimationStyle = VerticalAnimationStyle.ToRight
    ) : Orientation()

    data class Horizontal(val alignment: HorizontalAlignment = HorizontalAlignment.StartToEnd,
                          val animationStyle: HorizontalAnimationStyle = HorizontalAnimationStyle.FromTop
    ) : Orientation()
}