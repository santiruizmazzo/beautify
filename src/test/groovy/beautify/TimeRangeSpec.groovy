package beautify

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import java.time.*
import beautify.TimeRange.InvalidTimeRangeException

class TimeRangeSpec extends Specification implements DomainUnitTest<TimeRange> {

    def setup() {
    }

    def cleanup() {
    }

    void "Start of TimeRange that is greater than its end should fail"() {
        LocalDateTime from = LocalDateTime.of(2023, 6, 10, 0, 0)
        LocalDateTime to = LocalDateTime.of(2023, 6, 1, 0, 0)
        
        when:
            new TimeRange(from, to)
        then:
            thrown(InvalidTimeRangeException)
    }

    void "TimeRange A is within TimeRange B"() {
        TimeRange a = new TimeRange(LocalDateTime.of(2023, 6, 2, 0, 0), LocalDateTime.of(2023, 6, 10, 0, 0))
        TimeRange b = new TimeRange(LocalDateTime.of(2023, 6, 1, 0, 0), LocalDateTime.of(2023, 6, 11, 0, 0))

        expect:
            a.isWithin(b)
    }

    void "TimeRange A is within TimeRange B but the inverse is false"() {
        TimeRange a = new TimeRange(LocalDateTime.of(2023, 6, 2, 0, 0), LocalDateTime.of(2023, 6, 10, 0, 0))
        TimeRange b = new TimeRange(LocalDateTime.of(2023, 6, 1, 0, 0), LocalDateTime.of(2023, 6, 11, 0, 0))

        expect:
            a.isWithin(b) && !b.isWithin(a)
    }

    void "TimeRange A is partially within TimeRange B"() {
        TimeRange a = new TimeRange(LocalDateTime.of(2023, 5, 30, 0, 0), LocalDateTime.of(2023, 6, 10, 0, 0))
        TimeRange b = new TimeRange(LocalDateTime.of(2023, 6, 1, 0, 0), LocalDateTime.of(2023, 6, 11, 0, 0))

        expect:
            !a.isWithin(b)
    }

    void "TimeRange A overlaps TimeRange B"() {
        TimeRange a = new TimeRange(LocalDateTime.of(2023, 6, 5, 0, 0), LocalDateTime.of(2023, 6, 20, 0, 0))
        TimeRange b = new TimeRange(LocalDateTime.of(2023, 6, 1, 0, 0), LocalDateTime.of(2023, 6, 11, 0, 0))

        expect:
            a.overlaps(b)
    }

    void "TimeRange overlapping is bidirectional"() {
        TimeRange a = new TimeRange(LocalDateTime.of(2023, 6, 5, 0, 0), LocalDateTime.of(2023, 6, 20, 0, 0))
        TimeRange b = new TimeRange(LocalDateTime.of(2023, 6, 1, 0, 0), LocalDateTime.of(2023, 6, 11, 0, 0))

        expect:
            a.overlaps(b) && b.overlaps(a)
    }

    void "TimeRange A and TimeRange B do not overlap"() {
        TimeRange a = new TimeRange(LocalDateTime.of(2023, 6, 21, 0, 0), LocalDateTime.of(2023, 6, 24, 0, 0))
        TimeRange b = new TimeRange(LocalDateTime.of(2023, 6, 1, 0, 0), LocalDateTime.of(2023, 6, 11, 0, 0))

        expect:
            !a.overlaps(b)
    }
}
