package time_range

import java.time.*
import java.time.format.*

class TimeRange {

    LocalDateTime start
    LocalDateTime end

    TimeRange(){}

    TimeRange(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new InvalidTimeRangeException("El inicio del rango de tiempo es posterior a su final")
        }
        
        this.start = start
        this.end = end
    }

    @Override
    String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMM uuuu")
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        String formattedDate = start.toLocalDate().format(dateFormatter)
        String formattedStartTime = start.toLocalTime().format(timeFormatter)
        String formattedEndTime = end.toLocalTime().format(timeFormatter)
        "${formattedDate}: ${formattedStartTime} - ${formattedEndTime}"
    }

    static class InvalidTimeRangeException extends RuntimeException {
        InvalidTimeRangeException(String errorMessage) {
            super(errorMessage)
        }
    }

    Duration duration() {
        Duration.between(start, end)
    }

    Boolean isWithin(TimeRange timeRange) {
        start.isAfter(timeRange.start) && end.isBefore(timeRange.end)
    }

    Boolean precedesAndIntersects(TimeRange timeRange) {
        start.isBefore(timeRange.start) && end.isAfter(timeRange.start) && end.isBefore(timeRange.end)
    }

    Boolean overlaps(TimeRange timeRange) {
        isWithin(timeRange) || 
        precedesAndIntersects(timeRange) || 
        timeRange.isWithin(this) || 
        timeRange.precedesAndIntersects(this) ||

        start.isAfter(timeRange.start) && start.isBefore(timeRange.end) ||
        timeRange.start.isAfter(start) && timeRange.start.isBefore(end) ||
        end.isAfter(timeRange.start) && end.isBefore(timeRange.end) ||
        timeRange.end.isAfter(start) && timeRange.end.isBefore(end) ||
        start.equals(timeRange.start) ||
        timeRange.start.equals(start) ||
        end.equals(timeRange.end) ||
        timeRange.end.equals(end)
    }
}
