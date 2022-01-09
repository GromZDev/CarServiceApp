package carService.app.data.model.organization

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkingTime(
    var workingDayMonday: WorkingDays? = null,
    var workingTimeMonday: String? = null,
    var workingDayTuesday: WorkingDays? = null,
    var workingTimeTuesday: String? = null,
    var workingDayWednesday: WorkingDays? = null,
    var workingTimeWednesday: String? = null,
    var workingDayThursday: WorkingDays? = null,
    var workingTimeThursday: String? = null,
    var workingDayFriday: WorkingDays? = null,
    var workingTimeFriday: String? = null,
    var workingDaySaturday: WorkingDays? = null,
    var workingTimeSaturday: String? = null,
    var workingDaySunday: WorkingDays? = null,
    var workingTimeSunday: String? = null,
) : Parcelable {

    enum class WorkingDays {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }
}

fun getWorkingTime() = WorkingTime(
    WorkingTime.WorkingDays.MONDAY, "9:00 - 20:00",
    WorkingTime.WorkingDays.TUESDAY, "9:00 - 20:00",
    WorkingTime.WorkingDays.WEDNESDAY, "9:00 - 20:00",
    WorkingTime.WorkingDays.THURSDAY, "9:00 - 20:00",
    WorkingTime.WorkingDays.FRIDAY, "9:00 - 20:00",
    WorkingTime.WorkingDays.SATURDAY, "9:00 - 20:00",
    WorkingTime.WorkingDays.SUNDAY, "9:00 - 20:00",
)