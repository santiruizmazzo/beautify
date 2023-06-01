package beautify

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import java.time.*

class AppointmentTimeDetailSpec extends Specification implements DomainUnitTest<AppointmentTimeDetail> {

    def setup() {
    }

    def cleanup() {
    }

    void "AppointmentTimeDetail represents correct day of the week"() {
        LocalDateTime date = LocalDateTime.of(2023, 6, 1, 0, 0)
        TimeRange timeRange = new TimeRange(LocalDateTime.of(2023, 6, 1, 0, 0), LocalDateTime.of(2023, 6, 1, 10, 0))
        DayOfWeek thursday = DayOfWeek.THURSDAY
        AppointmentTimeDetail timeDetail = new AppointmentTimeDetail(date: date, timeRange: timeRange)

        expect:
            timeDetail.represents(thursday)
    }
}
