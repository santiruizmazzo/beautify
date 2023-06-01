package beautify
import java.time.*

class AppointmentTimeDetail {

    LocalDateTime date
    TimeRange timeRange

    static constraints = {
    }

    DayOfWeek dayOfWeek() {
        this.date.getDayOfWeek()
    }

    Duration duration() {
        timeRange.duration()
    }

    Boolean isWithin(TimeRange timeRange) {
        this.timeRange.isWithin(timeRange)
    }
}
