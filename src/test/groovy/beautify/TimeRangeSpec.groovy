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
        LocalTime from = LocalTime.of(5, 0)
        LocalTime to = LocalTime.of(0, 0)
        
        when:
            new TimeRange(from, to)
        then:
            thrown(InvalidTimeRangeException)
    }

    void "TimeRange A is within TimeRange B"() {
        TimeRange a = new TimeRange(LocalTime.of(2, 0), LocalTime.of(5, 0))
        TimeRange b = new TimeRange(LocalTime.of(1, 0), LocalTime.of(6, 0))

        expect:
            a.isWithin(b)
    }

    void "TimeRange A is within TimeRange B but the inverse is false"() {
        TimeRange a = new TimeRange(LocalTime.of(2, 0), LocalTime.of(5, 0))
        TimeRange b = new TimeRange(LocalTime.of(1, 0), LocalTime.of(6, 0))

        expect:
            a.isWithin(b) && !b.isWithin(a)
    }

    void "TimeRange A is partially within TimeRange B"() {
        TimeRange a = new TimeRange(LocalTime.of(0, 0), LocalTime.of(7, 0))
        TimeRange b = new TimeRange(LocalTime.of(3, 0), LocalTime.of(9, 0))

        expect:
            !a.isWithin(b)
    }

    void "TimeRange A overlaps TimeRange B"() {
        TimeRange a = new TimeRange(LocalTime.of(2, 0), LocalTime.of(6, 0))
        TimeRange b = new TimeRange(LocalTime.of(0, 0), LocalTime.of(5, 0))

        expect:
            a.overlaps(b)
    }

    void "TimeRange overlapping is bidirectional"() {
        TimeRange a = new TimeRange(LocalTime.of(2, 0), LocalTime.of(6, 0))
        TimeRange b = new TimeRange(LocalTime.of(0, 0), LocalTime.of(5, 0))

        expect:
            a.overlaps(b) && b.overlaps(a)
    }

    void "TimeRange A and TimeRange B do not overlap"() {
        TimeRange a = new TimeRange(LocalTime.of(10, 0), LocalTime.of(15, 0))
        TimeRange b = new TimeRange(LocalTime.of(20, 0), LocalTime.of(22, 0))

        expect:
            !a.overlaps(b)
    }
}
