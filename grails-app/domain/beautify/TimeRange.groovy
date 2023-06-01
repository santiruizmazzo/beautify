package beautify
import java.time.*

class TimeRange {

    LocalDateTime startTime
    LocalDateTime endTime

    TimeRangeConstructor(startTime, endTime) {
        this.startTime = startTime
        this.endTime = endTime
    }

    static constraints = {
    }
}
