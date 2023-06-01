package beautify
import java.time.*

class AppointmentTimeDetail {

    LocalDateTime date
    TimeRange timeRange

    static constraints = {
    }

    Boolean represents(DayOfWeek dayOfWeek) {
        this.date.getDayOfWeek() == dayOfWeek
    }
}
