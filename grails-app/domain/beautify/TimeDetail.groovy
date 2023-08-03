package beautify

import java.time.*

class TimeDetail {

    LocalDate date
    TimeRange timeRange

    LocalDateTime endDateTime() {
        LocalDateTime.of(date, timeRange.to)
    }

    Boolean overlaps(TimeDetail timeDetail) {
        date == timeDetail.date && timeRange.overlaps(timeDetail.timeRange)
    }
}
