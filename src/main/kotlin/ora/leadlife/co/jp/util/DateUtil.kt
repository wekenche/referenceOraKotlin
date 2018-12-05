package ora.leadlife.co.jp.util

import org.springframework.stereotype.Service
import java.util.*

@Service
class DateUtil {
    companion object {
        fun compareToDateOnly(cal1: Calendar, cal2: Calendar): Int {
            return toDateString(cal1).compareTo(toDateString(cal2))
        }

        fun clearTime(cal: Calendar): Calendar {
            cal.clear(Calendar.AM_PM)
            cal.clear(Calendar.HOUR_OF_DAY)
            cal.clear(Calendar.HOUR)
            cal.clear(Calendar.MINUTE)
            cal.clear(Calendar.SECOND)
            cal.clear(Calendar.MILLISECOND)
            return cal
        }

        fun toDateString(cal: Calendar): String {
            return "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH) + 1}-${cal.get(Calendar.DATE)}"
        }
    }
}