package beautify

import spock.lang.Specification
import java.time.*
import time_range.*

class TimeRangeSpec extends Specification {

    void "start of time range that is greater than its end should fail"() {
        given:
            LocalDateTime start = LocalDateTime.of(2023, 1, 1, 10, 0)
            LocalDateTime end = LocalDateTime.of(2023, 1, 1, 5, 0)
        
        when:
            new TimeRange(start, end)

        then:
            thrown(TimeRange.InvalidTimeRangeException)
    }

    void "time range A is within time range B"() {
        given:
            TimeRange a = new TimeRange(LocalDateTime.of(2023, 1, 1, 5, 0), LocalDateTime.of(2023, 1, 1, 6, 0))
            TimeRange b = new TimeRange(LocalDateTime.of(2023, 1, 1, 1, 0), LocalDateTime.of(2023, 1, 1, 7, 0))

        expect:
            a.isWithin(b)
    }

    void "time range A is within time range B but the inverse is false"() {
        given:
            TimeRange a = new TimeRange(LocalDateTime.of(2023, 1, 1, 5, 0), LocalDateTime.of(2023, 1, 1, 6, 0))
            TimeRange b = new TimeRange(LocalDateTime.of(2023, 1, 1, 1, 0), LocalDateTime.of(2023, 1, 1, 7, 0))

        expect:
            a.isWithin(b) && !b.isWithin(a)
    }

    void "time range A overlaps B because its start is within time range B"() {
        given:
            TimeRange a = new TimeRange(LocalDateTime.of(2023, 1, 5, 0, 0), LocalDateTime.of(2023, 1, 20, 0, 0))
            TimeRange b = new TimeRange(LocalDateTime.of(2023, 1, 1, 0, 0), LocalDateTime.of(2023, 1, 7, 0, 0))

        expect:
            a.overlaps(b)
    }

    void "time range A overlaps B because its end is within time range B"() {
        given:
            TimeRange a = new TimeRange(LocalDateTime.of(2023, 1, 1, 0, 0), LocalDateTime.of(2023, 1, 6, 0, 0))
            TimeRange b = new TimeRange(LocalDateTime.of(2023, 1, 5, 0, 0), LocalDateTime.of(2023, 1, 7, 0, 0))

        expect:
            a.overlaps(b)
    }

    void "time range A overlaps B because they have the same starts and ends"() {
        given:
            TimeRange a = new TimeRange(LocalDateTime.of(2023, 1, 1, 0, 0), LocalDateTime.of(2023, 1, 7, 0, 0))
            TimeRange b = new TimeRange(LocalDateTime.of(2023, 1, 1, 0, 0), LocalDateTime.of(2023, 1, 7, 0, 0))

        expect:
            a.overlaps(b)
    }

    void "time range A overlaps B because its start is the same as B's"() {
        given:
            TimeRange a = new TimeRange(LocalDateTime.of(2023, 1, 1, 0, 0), LocalDateTime.of(2023, 1, 7, 0, 0))
            TimeRange b = new TimeRange(LocalDateTime.of(2023, 1, 1, 0, 0), LocalDateTime.of(2023, 1, 27, 0, 0))

        expect:
            a.overlaps(b)
    }

    void "time range A overlaps B because its end is the same as B's"() {
        given:
            TimeRange a = new TimeRange(LocalDateTime.of(2023, 1, 10, 0, 0), LocalDateTime.of(2023, 1, 27, 0, 0))
            TimeRange b = new TimeRange(LocalDateTime.of(2023, 1, 1, 0, 0), LocalDateTime.of(2023, 1, 27, 0, 0))

        expect:
            a.overlaps(b)
    }

    void "time range overlapping is bidirectional"() {
        given:
            TimeRange a = new TimeRange(LocalDateTime.of(2023, 1, 10, 0, 0), LocalDateTime.of(2023, 1, 27, 0, 0))
            TimeRange b = new TimeRange(LocalDateTime.of(2023, 1, 1, 0, 0), LocalDateTime.of(2023, 1, 27, 0, 0))

        expect:
            a.overlaps(b) && b.overlaps(a)
    }

    void "time range A and time range B do not overlap"() {
        given:
            TimeRange a = new TimeRange(LocalDateTime.of(2023, 1, 10, 0, 0), LocalDateTime.of(2023, 1, 15, 0, 0))
            TimeRange b = new TimeRange(LocalDateTime.of(2023, 1, 16, 0, 0), LocalDateTime.of(2023, 1, 17, 0, 0))

        expect:
            !a.overlaps(b)
    }
}
