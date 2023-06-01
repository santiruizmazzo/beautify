package beautify
import java.time.*

class TimeRange {

    LocalDateTime from
    LocalDateTime to

    TimeRange(from, to) {
        if (from > to) {
            throw new InvalidTimeRangeException("El inicio del rango de tiempo es posterior a su final")
        }
        
        this.from = from
        this.to = to
    }

    static class InvalidTimeRangeException extends Exception {
        InvalidTimeRangeException(String errorMessage) {
            super(errorMessage)
        }
    }

    static constraints = {
    }

    Duration duration() {
        Duration.between(from, to)
    }

    Boolean isWithin(timeRange) {
        timeRange.from < this.from && this.to < timeRange.to
    }

    Boolean overlaps(timeRange) {
        this.isWithin(timeRange) || 
        timeRange.isWithin(this) || 
        this.precedesAndIntersects(timeRange) || 
        timeRange.precedesAndIntersects(this)
    }

    Boolean precedesAndIntersects(timeRange) {
        this.from < timeRange.from && this.to > timeRange.from && this.to < timeRange.to
    }
}
