package beautify

import java.time.*

class TimeRange {

    LocalTime from
    LocalTime to

    TimeRange(LocalTime from, LocalTime to) {
        if (from > to) {
            throw new InvalidTimeRangeException("El inicio del rango de tiempo es posterior a su final")
        }
        
        this.from = from
        this.to = to
    }

    static class InvalidTimeRangeException extends RuntimeException {
        InvalidTimeRangeException(String errorMessage) {
            super(errorMessage)
        }
    }

    Duration duration() {
        Duration.between(from, to)
    }

    Boolean isWithin(TimeRange timeRange) {
        from.isAfter(timeRange.from) && to.isBefore(timeRange.to)
    }

    Boolean precedesAndIntersects(TimeRange timeRange) {
        from.isBefore(timeRange.from) && to.isAfter(timeRange.from) && to.isBefore(timeRange.to)
    }

    Boolean overlaps(TimeRange timeRange) {
        isWithin(timeRange) || 
        timeRange.isWithin(this) || 
        precedesAndIntersects(timeRange) || 
        timeRange.precedesAndIntersects(this)
    }
}
